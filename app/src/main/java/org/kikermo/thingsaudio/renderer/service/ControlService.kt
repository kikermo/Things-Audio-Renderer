package org.kikermo.thingsaudio.renderer.service

import dagger.android.AndroidInjection
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
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
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.base.BaseService
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.renderer.api.PlayerControlActions
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ControlService : BaseService() {
    private lateinit var restServer: NettyApplicationEngine

    @Inject lateinit var playerControlActionsSubject: PublishSubject<PlayerControlActions>
    @Inject lateinit var trackListObservable: Observable<List<Track>>

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
        install(DefaultHeaders)
        install(Compression)
        install(CallLogging)
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
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
                skipNextReceived()
                call.respond(HttpStatusCode.OK)
            }
            post("/songs") {
                val trackList = call.receive<List<Track>>()
                listReceived(trackList)
                call.respond(HttpStatusCode.Accepted, trackList)
            }
            post("/song") {
                val track = call.receive<Track>()
                listReceived(listOf(track))
                call.respond(HttpStatusCode.Accepted, track)
            }
            get("/songs") {
                val trackList = trackListObservable.blockingLast()
                call.respond(HttpStatusCode.Accepted, trackList)
            }
        }
    }

    fun skipNextReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipNext)

    fun skipPrevReceived() = playerControlActionsSubject.onNext(PlayerControlActions.SkipPrevious)

    fun listReceived(trackList: List<Track>) =
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrackList(trackList))

    fun playReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Play)

    fun pauseReceived() = playerControlActionsSubject.onNext(PlayerControlActions.Pause)
}
