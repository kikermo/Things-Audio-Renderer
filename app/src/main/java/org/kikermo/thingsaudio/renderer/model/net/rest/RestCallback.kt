package org.kikermo.thingsaudio.renderer.model.net.rest

import org.kikermo.thingsaudio.core.model.Track

interface RestCallback {

    fun skipNextReceived()

    fun skipPrevReceived()

    fun listReceived(trackList: List<Track>)

    fun playReceived()

    fun pauseReceived()
}
