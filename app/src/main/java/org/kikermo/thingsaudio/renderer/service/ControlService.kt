package org.kikermo.thingsaudio.renderer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.renderer.model.PlayerControlActions
import org.kikermo.thingsaudio.renderer.model.net.rest.RestCallback
import org.kikermo.thingsaudio.renderer.model.net.rest.RestServer
import javax.inject.Inject

class ControlService : Service(), RestCallback {
    private lateinit var restServer: RestServer

    @Inject
    lateinit var playerControlActionsSubject: PublishSubject<PlayerControlActions>

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        startRestServer()
    }

    override fun onDestroy() {
        stopRestServer()
        super.onDestroy()
    }

    private fun startRestServer() {
        restServer = RestServer(8080)
        restServer.setRestCallback(this)
        restServer.start()
    }

    private fun stopRestServer() {
        restServer.stop()
    }

    override fun skipNextReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipNext)

    override fun skipPrevReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipPrevious)

    override fun listReceived(trackList: List<Track>) =
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrackList(trackList))

    override fun playReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Play)

    override fun pauseReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Pause)
}
