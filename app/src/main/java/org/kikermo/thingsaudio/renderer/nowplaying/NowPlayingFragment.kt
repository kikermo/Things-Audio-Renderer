package org.kikermo.thingsaudio.renderer.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_now_playing.*
import org.kikermo.thingsaudio.core.base.BaseFragmentWithPresenter
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.utils.toFormattedSeconds
import org.kikermo.thingsaudioreceiver.R

class NowPlayingFragment : BaseFragmentWithPresenter<NowPlayingContract.View, NowPlayingContract.Presenter>(),
    NowPlayingContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_now_playing, container, false)

    override fun updateTrack(track: Track) {
        now_playing_track_name.text = track.title
        now_playing_artist_name.text = track.artist
        now_playing_artist_name.text = track.album

        now_playing_track_lenght.text = track.length.toFormattedSeconds()
        now_playing_progress_bar.max = track.length

        Picasso.with(context)
            .load(track.art)
            .into(now_playing_art)
    }

    override fun updatePosition(position: Int) {
        now_playing_progress_bar.progress = position
        now_playing_progress_text.text = position.toFormattedSeconds()
    }

    override fun updatePlayState(playState: PlayState) {
    }
}
