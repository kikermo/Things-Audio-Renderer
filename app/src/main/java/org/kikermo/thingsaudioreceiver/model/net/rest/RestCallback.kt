package org.kikermo.thingsaudioreceiver.model.net.rest

import org.kikermo.thingsaudio.core.model.Track

interface RestCallback {

    fun skipNextReceived()

    fun skipPrevReceived()

    fun listReceived(trackList: List<Track>)

    fun playReceived()

    fun pauseReceived()
}
