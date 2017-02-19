package org.kikermo.thingsaudioreceiver.model;

import android.content.Context;
import android.content.IntentFilter;

import com.f2prateek.rx.receivers.RxBroadcastReceiver;

import org.kikermo.thingsaudioreceiver.model.data.PlayPosition;
import org.kikermo.thingsaudioreceiver.model.data.PlayState;
import org.kikermo.thingsaudioreceiver.model.data.PlaybackEvent;
import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.util.Utils;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.kikermo.thingsaudioreceiver.util.Constants.BA_PLAYBACKEVENT;
import static org.kikermo.thingsaudioreceiver.util.Constants.BK_PLAYBACKEVENT;


public class ReceiberRepositoryImp implements ReceiverRepository {
    //TODO evalueate potential context leak
    private Observable<PlaybackEvent> playbackEventObservable;

    public ReceiberRepositoryImp(Context context) {
        this.playbackEventObservable = RxBroadcastReceiver.create(context, new IntentFilter(BA_PLAYBACKEVENT))
                .subscribeOn(Schedulers.io())
                .filter(Utils::notNull)
                .map(intent -> (PlaybackEvent) intent.getParcelableExtra(BK_PLAYBACKEVENT))
                .share();
    }

    @Override
    public Observable<Track> subscribeToTrackUpdates() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof Track)
                .cast(Track.class);
    }

    @Override
    public Observable<PlayState> subscribeToPlayState() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof PlayState)
                .cast(PlayState.class);
    }

    @Override
    public Observable<PlayPosition> subscribeToPlayPosition() {
        return playbackEventObservable
                .filter(playbackEvent -> playbackEvent instanceof PlayPosition)
                .cast(PlayPosition.class);
    }
}
