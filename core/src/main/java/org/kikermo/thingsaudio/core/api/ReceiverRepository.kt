package org.kikermo.thingsaudio.core.api

import org.kikermo.thingsaudio.core.model.PlayPosition
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track

interface ReceiverRepository {

    fun subscribeToTrackUpdates(): Observable<Track>

    fun subscribeToPlayState(): Observable<PlayState>

    fun subscribeToPlayPosition(): Observable<PlayPosition>
}
