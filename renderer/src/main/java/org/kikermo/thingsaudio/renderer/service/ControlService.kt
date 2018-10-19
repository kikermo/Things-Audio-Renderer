package org.kikermo.thingsaudio.renderer.service

import com.google.gson.Gson
import dagger.android.AndroidInjection
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.Frame
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import io.reactivex.Observable
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.base.BaseService
import org.kikermo.thingsaudio.core.model.Track
import timber.log.Timber
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ControlService : BaseService() {
    private lateinit var restServer: NettyApplicationEngine

    @Inject lateinit var trackListObservable: Observable<List<Track>>
    @Inject lateinit var receiverRepository: ReceiverRepository
    @Inject lateinit var gson: Gson

    override fun onCreate() {
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
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(60)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
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
            get("/control/stop") {
                stopReceived()
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
            post("/tracks") {
                val trackList = call.receive<List<Track>>()
                listReceived(trackList)
                call.respond(HttpStatusCode.Accepted, trackList)
            }
            post("/track") {
                val track = call.receive<Track>()
                listReceived(listOf(track))
                call.respond(HttpStatusCode.Accepted, track)
            }
            get("/tracks") {
                val trackList = trackListObservable.blockingLast()
                call.respond(HttpStatusCode.Accepted, trackList)
            }
            webSocket("/player/position") {
                while (true) {
                    registerDisposable(receiverRepository.getPlayPositionUpdates().subscribe {
                        outgoing.offer(it.toJsonFrame())
                    })
                }
            }
            webSocket("/player/track") {
                while (true) {
                    registerDisposable(receiverRepository.getTrackUpdates().subscribe {
                        outgoing.offer(it.toJsonFrame())
                    })
                }
            }
            webSocket("/player/state") {
                while (true) {
                    registerDisposable(receiverRepository.getPlayStateUpdates().subscribe {
                        outgoing.offer(it.toJsonFrame())
                    })
                }
            }
        }
    }

    private fun skipNextReceived() = registerDisposable(receiverRepository.sendSkipNextCommand().subscribe({}, Timber::e))

    private fun skipPrevReceived() = registerDisposable(receiverRepository.sendSkipPreviousCommand().subscribe({}, Timber::e))

    private fun listReceived(trackList: List<Track>) =
        registerDisposable(receiverRepository.addTrackList(trackList).subscribe({}, Timber::e))

    private fun playReceived() = registerDisposable(receiverRepository.sendPlayCommand().subscribe({}, Timber::e))

    private fun pauseReceived() = registerDisposable(receiverRepository.sendPauseCommand().subscribe({}, Timber::e))

    private fun stopReceived() = registerDisposable(receiverRepository.sendStopCommand().subscribe({}, Timber::e))

    private fun Any.toJsonFrame(): Frame.Text {
        return Frame.Text(gson.toJson(this))
    }
}
