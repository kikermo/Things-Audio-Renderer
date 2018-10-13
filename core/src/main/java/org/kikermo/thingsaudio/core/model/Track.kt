package org.kikermo.thingsaudio.core.model

data class Track(
    val uuid: String,
    val title: String? = null,
    var album: String? = null,
    var artist: String? = null,
    var length: Int = 0,
    var url: String,
    var art: String? = null
)
