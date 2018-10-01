package org.kikermo.thingsaudioreceiver.model

import org.kikermo.thingsaudio.core.api.model.Track

sealed class PlayerControlActions {
    object Pause : PlayerControlActions()
    object Play : PlayerControlActions()
    object SkipNext : PlayerControlActions()
    object SkipPrevious : PlayerControlActions()
    data class AddSong(val track: Track) : PlayerControlActions()
    data class AddTrackList(val trackList: List<Track>) : PlayerControlActions()
}