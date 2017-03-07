package org.kikermo.thingsaudioreceiver.nowplaying;

import org.kikermo.thingsaudioreceiver.model.ReceiverRepository;
import org.kikermo.thingsaudioreceiver.util.Log;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by EnriqueR on 17/02/2017.
 */

public class NowPlayingPresenter implements NowPlayingContract.Presenter {
    private ReceiverRepository repository;
    private NowPlayingContract.View view;

    private CompositeDisposable compositeDisposable;


    public NowPlayingPresenter(NowPlayingContract.View view, ReceiverRepository repository) {
        this.repository = repository;
        this.view = view;

        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        compositeDisposable.add(subscribeToPlayPosition());
        compositeDisposable.add(subscribeToPlayStateChanges());
        compositeDisposable.add(subscribeToTrackUpdates());
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }


    private Disposable subscribeToPlayPosition() {
        return repository.subscribeToPlayPosition()
                .subscribe(playPosition -> view.updatePosition(playPosition),
                        Log::logThrowable);
    }

    private Disposable subscribeToTrackUpdates() {
        return repository.subscribeToTrackUpdates()
                .subscribe(track -> view.updateTrack(track),
                        Log::logThrowable);
    }

    private Disposable subscribeToPlayStateChanges() {
        return repository.subscribeToPlayState()
                .subscribe(playState -> view.updatePlayState(playState),
                        Log::logThrowable);
    }
}
