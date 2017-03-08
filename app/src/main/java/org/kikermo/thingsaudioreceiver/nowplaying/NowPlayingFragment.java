package org.kikermo.thingsaudioreceiver.nowplaying;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.kikermo.thingsaudioreceiver.R;
import org.kikermo.thingsaudioreceiver.model.data.PlayPosition;
import org.kikermo.thingsaudioreceiver.model.data.PlayState;
import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.util.Utils;


public class NowPlayingFragment extends Fragment implements NowPlayingContract.View {
    private TextView text1, text2, text3, songLength, progressVal;
    private ProgressBar progress;
    private ImageView art;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);
        text1 = (TextView) v.findViewById(R.id.text1);
        text2 = (TextView) v.findViewById(R.id.text2);
        text3 = (TextView) v.findViewById(R.id.text3);
        progressVal = (TextView) v.findViewById(R.id.trackPointer);
        songLength = (TextView) v.findViewById(R.id.songLength);
        progress = (ProgressBar) v.findViewById(R.id.songProgress);
        art = (ImageView) v.findViewById(R.id.art);
        return v;
    }

    @Override
    public void updateTrack(Track track) {
        text1.setText(track.getTitle());
        text2.setText(track.getArtist());
        text3.setText(track.getAlbum());

        songLength.setText(Utils.formatSeconds(track.getLength()));
        progress.setMax(track.getLength());

        Picasso.with(getContext())
                .load(track.getArt())
                .into(art);

    }

    @Override
    public void updatePosition(PlayPosition position) {
        progress.setProgress(position.getPosition());
        progressVal.setText(position.getFormattedPosition());
    }

    @Override
    public void updatePlayState(PlayState playState) {

    }

    @Override
    public void setBasePresenter(NowPlayingContract.Presenter presenter) {

    }
}
