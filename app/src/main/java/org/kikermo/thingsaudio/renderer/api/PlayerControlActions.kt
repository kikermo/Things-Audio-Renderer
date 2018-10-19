package org.kikermo.thingsaudio.renderer.api

import org.kikermo.thingsaudio.core.model.Track

sealed class PlayerControlActions {
    object Pause : PlayerControlActions()
    object Stop : PlayerControlActions()
    object Play : PlayerControlActions()
    data class SkipNext(val trackNumber: Int = 1) : PlayerControlActions()
    data class SkipPrevious(val trackNumber: Int = 1) : PlayerControlActions()
    object ClearTackList : PlayerControlActions()
    data class AddTrack(val track: Track) : PlayerControlActions()
    data class DeleteTrack(val track: Track) : PlayerControlActions()
    data class AddTrackList(val trackList: List<Track>) : PlayerControlActions()
}