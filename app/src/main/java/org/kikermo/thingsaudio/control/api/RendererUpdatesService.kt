package org.kikermo.thingsaudio.control.api

import io.reactivex.Observable
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track

interface RendererUpdatesService {

    @WebsocketPath("/player/track")
    fun getTrackUpdates(): Observable<Track>

    @WebsocketPath("/player/state")
    fun getPlayStateUpdates(): Observable<PlayState>

    @WebsocketPath("/player/position")
    fun getPlayPositionUpdates(): Observable<Int>
}