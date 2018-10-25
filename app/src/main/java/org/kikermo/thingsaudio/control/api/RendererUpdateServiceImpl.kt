package org.kikermo.thingsaudio.control.api

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.*
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import timber.log.Timber
import javax.inject.Inject

class RendererUpdateServiceImpl @Inject constructor(val okHttpClient: OkHttpClient,
                                                    val gson: Gson)
    : RendererUpdatesService {

    override fun getTrackUpdates(): Observable<Track> {
        val path = javaClass.getAnnotation(WebsocketPath::class.java).value
        return getObservalbleWebsocket(path = path, clazz = Track::class.java)
    }

    override fun getPlayStateUpdates(): Observable<PlayState> {
        val path = javaClass.getAnnotation(WebsocketPath::class.java).value
        return getObservalbleWebsocket(path = path, clazz = PlayState::class.java)
    }

    override fun getPlayPositionUpdates(): Observable<Int> {
        val path = javaClass.getAnnotation(WebsocketPath::class.java).value
        return getObservalbleWebsocket(path = path, clazz = Int::class.java)
    }

    private fun <T> getObservalbleWebsocket(path: String, clazz: Class<T>): Observable<T> {
        return Observable.create { emitter ->
            val request: Request
            val webSocket = WebSocket.Factory { request, listener ->

            }.newWebSocket(request, createWebsocketListener(emitter, clazz))
            emitter.setCancellable { webSocket.close(0, null) }
        }
    }

    private fun <T> createWebsocketListener(emitter: ObservableEmitter<T>, clazz: Class<T>): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Timber.d("Web socket open")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Timber.d("Web socket failure")
                emitter.onError(t)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val deserializedObject: T = gson.fromJson(text, clazz)
                emitter.onNext(deserializedObject)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                emitter.onComplete()
            }
        }
    }
}