package org.kikermo.thingsaudioreceiver.nowplaying

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.squareup.picasso.Picasso

import org.kikermo.thingsaudioreceiver.R
import org.kikermo.thingsaudioreceiver.model.data.PlayPosition
import org.kikermo.thingsaudioreceiver.model.data.PlayState
import org.kikermo.thingsaudioreceiver.model.data.Track
import org.kikermo.thingsaudioreceiver.util.Utils

class NowPlayingFragment : Fragment(), NowPlayingContract.View {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_now_playing, container, false)

    override fun updateTrack(track: Track) {
        now_playing_track_name.text = track.title
        now_playing_artist_name.text = track.artist
        now_playing_artist_name.text = track.album

        now_playing_track_lenght.text = Utils.formatSeconds(track.length)
        now_playing_progress_bar.max = track.length

        Picasso.with(context)
            .load(track.art)
            .into(now_playing_art)
    }

    override fun updatePosition(position: PlayPosition) {
        now_playing_progress_bar.progress = position.position
        now_playing_progress_text.text = position.formattedPosition
    }

    override fun updatePlayState(playState: PlayState) {
    }
}
