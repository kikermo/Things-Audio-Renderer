package org.kikermo.thingsaudioreceiver.nowplaying

import org.kikermo.thingsaudioreceiver.model.ReceiverRepository
import org.kikermo.thingsaudioreceiver.util.Log

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



class NowPlayingPresenter(private val view: NowPlayingContract.View,
                          private val repository: ReceiverRepository) : NowPlayingContract.Presenter {

    private val compositeDisposable: CompositeDisposable

    init {

        this.compositeDisposable = CompositeDisposable()
    }

    fun subscribe() {
        compositeDisposable.add(subscribeToPlayPosition())
        compositeDisposable.add(subscribeToPlayStateChanges())
        compositeDisposable.add(subscribeToTrackUpdates())
    }

    fun unsubscribe() {
        compositeDisposable.clear()
    }

    private fun subscribeToPlayPosition(): Disposable {
        return repository.subscribeToPlayPosition()
            .subscribe({ playPosition -> view.updatePosition(playPosition) },
                Consumer<Throwable> { Log.logThrowable(it) })
    }

    private fun subscribeToTrackUpdates(): Disposable {
        return repository.subscribeToTrackUpdates()
            .subscribe({ track -> view.updateTrack(track) },
                Consumer<Throwable> { Log.logThrowable(it) })
    }

    private fun subscribeToPlayStateChanges(): Disposable {
        return repository.subscribeToPlayState()
            .subscribe({ playState -> view.updatePlayState(playState) },
                Consumer<Throwable> { Log.logThrowable(it) })
    }
}
