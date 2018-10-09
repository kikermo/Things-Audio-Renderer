package org.kikermo.thingsaudio.renderer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.android.AndroidInjection
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson

import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.renderer.model.PlayerControlActions
import org.kikermo.thingsaudio.renderer.model.net.rest.RestCallback
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ControlService : Service(), RestCallback {
    private lateinit var restServer: NettyApplicationEngine

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
        restServer = createNettyApplication(8080)
        restServer.start()
    }

    private fun stopRestServer() {
        restServer.stop(1, 1, TimeUnit.SECONDS)
    }

    private fun createNettyApplication(port: Int) = embeddedServer(Netty, port) {
        install(ContentNegotiation) {
            gson {
            }
        }
        routing {
            get("/control/play") {
                playReceived()
                call.respond(HttpStatusCode.OK)
            }
            get("/control/pause") {
                pauseReceived()
                call.respond(HttpStatusCode.OK)
            }
            get("/control/skip_prev") {
                skipPrevReceived()
                call.respond(HttpStatusCode.OK)
            }
            get("/control/skip_next") {
                playReceived()
                call.respond(HttpStatusCode.Created)
            }
            post("/songs") {
                val trackList = call.receive<List<Track>>()
                listReceived(trackList)
                call.respond(HttpStatusCode.Accepted, trackList)
            }
            get("/songs") {
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    override fun skipNextReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipNext)

    override fun skipPrevReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipPrevious)

    override fun listReceived(trackList: List<Track>) =
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrackList(trackList))

    override fun playReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Play)

    override fun pauseReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Pause)
}
