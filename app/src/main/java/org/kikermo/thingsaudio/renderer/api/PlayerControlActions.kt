package org.kikermo.thingsaudio.renderer.api

import org.kikermo.thingsaudio.core.model.Track

sealed class PlayerControlActions {
    object Pause : PlayerControlActions()
    object Play : PlayerControlActions()
    object SkipNext : PlayerControlActions()
    object SkipPrevious : PlayerControlActions()
    data class AddSong(val track: Track) : PlayerControlActions()
    data class AddTrackList(val trackList: List<Track>) : PlayerControlActions()
}