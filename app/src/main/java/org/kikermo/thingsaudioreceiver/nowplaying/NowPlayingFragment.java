package org.kikermo.thingsaudioreceiver.nowplaying;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kikermo.thingsaudioreceiver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

}
