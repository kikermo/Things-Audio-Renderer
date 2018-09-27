package org.kikermo.thingsaudioreceiver.model.net.rest

import org.kikermo.thingsaudioreceiver.model.data.Track

/**
 * Created by EnriqueR on 28/02/2017.
 */

interface RestCallback {

    fun skipNextReceived()

    fun skipPrevReceived()

    fun listReceived(trackList: List<Track>)

    fun playReceived()

    fun pauseReceived()
}
