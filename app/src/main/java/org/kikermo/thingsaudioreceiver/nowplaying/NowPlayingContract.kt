package org.kikermo.thingsaudioreceiver.nowplaying

import org.kikermo.thingsaudio.core.base.IPresenter
import org.kikermo.thingsaudio.core.base.IView
import org.kikermo.thingsaudio.core.api.model.PlayState
import org.kikermo.thingsaudio.core.api.model.Track

interface NowPlayingContract {
    interface View : IView {

        fun updateTrack(track: Track)

        fun updatePosition(position: Int)

        fun updatePlayState(playState: PlayState)
    }

    interface Presenter : IPresenter<View>
}
