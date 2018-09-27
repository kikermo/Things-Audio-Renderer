package org.kikermo.thingsaudio.core.model

data class Track(
    val title: String? = null,
    var album: String? = null,
    var artist: String? = null,
    var length: Int = 0,
    var url: String? = null,
    var art: String? = null
) : PlaybackEvent()
