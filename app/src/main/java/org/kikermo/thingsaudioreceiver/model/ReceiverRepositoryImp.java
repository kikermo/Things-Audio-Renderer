package org.kikermo.thingsaudioreceiver.model;

import android.content.Context;
import android.content.IntentFilter;


import org.kikermo.thingsaudioreceiver.model.data.PlayPosition;
import org.kikermo.thingsaudioreceiver.model.data.PlayState;
import org.kikermo.thingsaudioreceiver.model.data.PlaybackEvent;
import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.util.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


import static org.kikermo.thingsaudioreceiver.util.Constants.BA_PLAYBACKEVENT;
import static org.kikermo.thingsaudioreceiver.util.Constants.BK_PLAYBACKEVENT;


public class ReceiverRepositoryImp implements ReceiverRepository {
    //TODO evalueate potential context leak
    private Observable<PlaybackEvent> playbackEventObservable;

    public ReceiverRepositoryImp(Context context) {
        this.playbackEventObservable = RxBroadcastReceiver.fromBroadcast(context, new IntentFilter(BA_PLAYBACKEVENT))
                .subscribeOn(Schedulers.io())
                .filter(Utils::notNull)
                .map(intent -> (PlaybackEvent) intent.getParcelableExtra(BK_PLAYBACKEVENT))
                .share();
    }

    @Override
    public Observable<Track> subscribeToTrackUpdates() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof Track)
                .cast(Track.class)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PlayState> subscribeToPlayState() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof PlayState)
                .cast(PlayState.class)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PlayPosition> subscribeToPlayPosition() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof PlayPosition)
                .cast(PlayPosition.class)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
