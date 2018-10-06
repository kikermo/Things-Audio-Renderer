package org.kikermo.thingsaudio.renderer.service

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.renderer.model.PlayerControlActions
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlayerService : Service(), MediaPlayer.OnCompletionListener {
    lateinit var mediaPlayer: MediaPlayer
    private val trackList: MutableList<Track> = mutableListOf()
    private var trackPointer: Int = 0
    private val repeatList: Boolean = false
    private val repeatSong: Boolean = false

    @Inject
    lateinit var trackSubject: BehaviorSubject<Track>
    @Inject
    lateinit var playerControlActionsObservable: Observable<PlayerControlActions>
    @Inject
    lateinit var playPositionSubject: BehaviorSubject<Int>

    private val compositeDisposable = CompositeDisposable()

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
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnCompletionListener(this)

        compositeDisposable.add(playerControlActionsObservable.subscribe(this::processAction, Timber::e))
        compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS)
            .map { mediaPlayer.currentPosition / 1000 }
            .distinctUntilChanged()
            .subscribe(playPositionSubject::onNext, Timber::e))
    }

    override fun onDestroy() {
        mediaPlayer.release()

        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        if (repeatSong) {
            mediaPlayer.start()
            return
        }

        if (trackPointer < trackList.size) {
            trackPointer++
            playCurrentSong()
        } else {
            trackPointer = 0
            if (repeatList)
                playCurrentSong()
        }
    }

    private fun playCurrentSong() {
        val track = trackList[trackPointer]

        trackSubject.onNext(track)

        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(track.url)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
