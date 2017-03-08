package org.kikermo.thingsaudioreceiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingContract;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingFragment;
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingPresenter;

public class MainActivity extends AppCompatActivity {
    NowPlayingContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NowPlayingFragment fragment = new NowPlayingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


        NowPlayingContract.View view = fragment;
        presenter = new NowPlayingPresenter(fragment, new ReceiverRepositoryImp(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        presenter.unsubscribe();

        super.onPause();
    }
}
