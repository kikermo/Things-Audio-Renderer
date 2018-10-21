package org.kikermo.thingsaudio.renderer.service

import android.media.AudioAttributes
import android.media.MediaPlayer
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.kikermo.thingsaudio.core.base.BaseService
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import org.kikermo.thingsaudio.renderer.api.PlayerControlActions
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlayerService : BaseService(), MediaPlayer.OnCompletionListener {

    private lateinit var mediaPlayer: MediaPlayer
    private val trackList: MutableList<Track> = mutableListOf()
    private var trackPointer: Int = 0
    private var trackPositionDisposable: Disposable? = null

    @Inject lateinit var trackSubject: BehaviorSubject<Track>
    @Inject lateinit var playerControlActionsObservable: Observable<PlayerControlActions>
    @Inject lateinit var playPositionSubject: BehaviorSubject<Int>
    @Inject lateinit var repeatModeObservable: Observable<RepeatMode>
    @Inject lateinit var trackListBehaviorSubject: BehaviorSubject<List<Track>>
    @Inject lateinit var rxSchedulers: RxSchedulers

    private fun processAction(playerControlActions: PlayerControlActions) {
        Timber.d("Action Received")
        when (playerControlActions) {
            is PlayerControlActions.Pause -> mediaPlayer.pause()
            is PlayerControlActions.Play -> mediaPlayer.start()
            is PlayerControlActions.SkipNext -> if (trackPointer < trackList.size) {
                trackPointer++
                playCurrentSong()
            }
            is PlayerControlActions.SkipPrevious -> if (trackPointer >= 1) {
                trackPointer--
                playCurrentSong()
            }
            is PlayerControlActions.AddTrack -> {
                Timber.d("New song")
                trackList.add(playerControlActions.track)
                if (!mediaPlayer.isPlaying) {
                    playCurrentSong()
                }
            }
            is PlayerControlActions.AddTrackList -> {
                Timber.d("New list")
                mediaPlayer.stop()
                trackList.clear()
                trackList.addAll(playerControlActions.trackList)
                trackListBehaviorSubject.onNext(trackList)
                trackPointer = 0
                playCurrentSong()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build())
        mediaPlayer.setOnCompletionListener(this)

        registerDisposable(playerControlActionsObservable
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe(this::processAction, Timber::e))
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        val repeatMode = repeatModeObservable.blockingLast()

        when (repeatMode) {
            RepeatMode.TRACK -> mediaPlayer.start()
            RepeatMode.ALL -> {
                trackPointer = 0
                playCurrentSong()
            }
            else -> {
                if (trackPointer < trackList.size) {
                    trackPointer++
                    playCurrentSong()
                } else {
                    trackPointer = 0
                }
            }
        }
    }

    private fun playCurrentSong() {
        val track = trackList.getOrNull(trackPointer)

        track?.run {
            trackSubject.onNext(this)
            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                Timber.e(e)
            }
            trackPositionDisposable?.run {
                if (!isDisposed) {
                    dispose()
                }
            }
            trackPositionDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .map { mediaPlayer.currentPosition / 1000 }
                .distinctUntilChanged()
                .subscribeOn(rxSchedulers.computation())
                .subscribe(playPositionSubject::onNext, Timber::e)
            registerDisposable(trackPositionDisposable!!)
        }
    }
}
