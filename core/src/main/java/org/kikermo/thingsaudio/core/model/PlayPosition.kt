package org.kikermo.thingsaudio.core.model

import org.kikermo.thingsaudio.core.utils.formatSeconds

data class PlayPosition(val position: Int = 0) : PlaybackEvent() {

    fun PlayPosition.toFormattedPosition() = position.formatSeconds()
}


