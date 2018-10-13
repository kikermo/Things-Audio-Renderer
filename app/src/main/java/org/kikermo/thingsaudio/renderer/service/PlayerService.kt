package org.kikermo.thingsaudio.renderer.service

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.kikermo.thingsaudio.core.base.BaseService
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.renderer.api.PlayerControlActions
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlayerService : BaseService(), MediaPlayer.OnCompletionListener {
    lateinit var mediaPlayer: MediaPlayer
    private val trackList: MutableList<Track> = mutableListOf()
    private var trackPointer: Int = 0

    @Inject lateinit var trackSubject: BehaviorSubject<Track>
    @Inject lateinit var playerControlActionsObservable: Observable<PlayerControlActions>
    @Inject lateinit var playPositionSubject: BehaviorSubject<Int>
    @Inject lateinit var repeatModeObservable: Observable<RepeatMode>

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
            is PlayerControlActions.AddSong -> {
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

        registerDisposable(playerControlActionsObservable.subscribe(this::processAction, Timber::e))
        registerDisposable(Observable.interval(1, TimeUnit.SECONDS)
            .map { mediaPlayer.currentPosition / 1000 }
            .distinctUntilChanged()
            .subscribe(playPositionSubject::onNext, Timber::e))
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        val repeatMode = repeatModeObservable.blockingLast()

        when(repeatMode){
            RepeatMode.TRACK -> mediaPlayer.start()
            RepeatMode.ALL -> {
                trackPointer = 0
                playCurrentSong()
            }
            else ->{
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
        }
    }
}
