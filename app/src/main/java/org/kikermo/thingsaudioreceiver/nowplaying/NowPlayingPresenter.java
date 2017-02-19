package org.kikermo.thingsaudioreceiver.nowplaying;

import android.content.Context;

import org.kikermo.thingsaudioreceiver.model.ReceiverRepository;
import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp;
import org.kikermo.thingsaudioreceiver.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by EnriqueR on 17/02/2017.
 */

public class NowPlayingPresenter implements NowPlayingContract.Presenter {
    private ReceiverRepository repository;
    private NowPlayingContract.View view;

    private CompositeSubscription compositeSubscription;


    public NowPlayingPresenter(Context context, NowPlayingContract.View view) {
        this.repository = new ReceiverRepositoryImp(context);
        this.view = view;

        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        compositeSubscription.add(subscribeToPlayPosition());
        compositeSubscription.add(subscribeToPlayStateChanges());
        compositeSubscription.add(subscribeToTrackUpdates());
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }


    private Subscription subscribeToPlayPosition() {
        return repository.subscribeToPlayPosition()
                .subscribe(playPosition -> view.updatePosition(playPosition),
                        Log::logThrowable);
    }

    private Subscription subscribeToTrackUpdates() {
        return repository.subscribeToTrackUpdates()
                .subscribe(track -> view.updateTrack(track),
                        Log::logThrowable);
    }

    private Subscription subscribeToPlayStateChanges() {
        return repository.subscribeToPlayState()
                .subscribe(playState -> view.updatePlayState(playState),
                        Log::logThrowable);
    }
}
