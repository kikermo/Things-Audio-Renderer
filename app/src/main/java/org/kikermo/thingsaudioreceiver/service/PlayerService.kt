package org.kikermo.thingsaudioreceiver.service

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.IBinder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudioreceiver.ThingsReceiverApplication
import org.kikermo.thingsaudioreceiver.model.PlayerControlActions
import org.kikermo.thingsaudioreceiver.util.Constants
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

class PlayerService : Service(), MediaPlayer.OnCompletionListener {
    lateinit var mediaPlayer: MediaPlayer
    private val trackList: MutableList<Track> = mutableListOf()
    private var trackPointer: Int = 0
    private val repeatList: Boolean
    private val repeatSong: Boolean

    lateinit var trackSubject: BehaviorSubject<Track>

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

    init {
        trackPointer = 0
        repeatList = false
        repeatSong = false
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnCompletionListener(this)

        val app = (application as ThingsReceiverApplication) //TODO add dagger

        val playerControlObservable = app.playerControlsObservable
        compositeDisposable.add(playerControlObservable.subscribe(this::processAction, Timber::e))

        trackSubject = app.trackSubject

        compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS)
            .map { mediaPlayer.currentPosition / 1000 }
            .distinctUntilChanged()
            .subscribe({ integer ->
                val playPosition = integer!!
                val posInt = Intent()
                posInt.putExtra(Constants.BK_PLAYBACKEVENT, playPosition)
                posInt.action = Constants.BA_PLAYBACKEVENT

                sendBroadcast(posInt)
                Timber.d("pos$integer")
            }, Timber::e))
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
