package org.kikermo.thingsaudioreceiver.nowplaying

import org.kikermo.thingsaudioreceiver.BasePresenter
import org.kikermo.thingsaudioreceiver.BaseView
import org.kikermo.thingsaudioreceiver.model.data.PlayPosition
import org.kikermo.thingsaudioreceiver.model.data.PlayState
import org.kikermo.thingsaudioreceiver.model.data.Track

interface NowPlayingContract {
    interface View : BaseView<Presenter> {

        fun updateTrack(track: Track)

        fun updatePosition(position: PlayPosition)

        fun updatePlayState(playState: PlayState)
    }

    interface Presenter : BasePresenter
}
