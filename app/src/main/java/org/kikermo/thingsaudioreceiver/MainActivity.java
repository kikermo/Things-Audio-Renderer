package org.kikermo.thingsaudioreceiver;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingContract;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingFragment;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NowPlayingFragment fragment = new NowPlayingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();


        NowPlayingContract.View view = fragment;
        NowPlayingContract.Presenter presenter = new NowPlayingPresenter(fragment,new ReceiverRepositoryImp(this));

    }
}
