package org.kikermo.thingsaudio.core.api

import io.reactivex.Observable
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track

interface ReceiverRepository {

    fun getTrackUpdates(): Observable<Track>

    fun getPlayStateUpdates(): Observable<PlayState>

    fun getPlayPositionUpdates(): Observable<Int>
}
