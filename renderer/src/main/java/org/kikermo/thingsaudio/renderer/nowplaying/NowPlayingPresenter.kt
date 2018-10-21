package org.kikermo.thingsaudio.renderer.nowplaying

import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.base.BasePresenter
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

class NowPlayingPresenter @Inject constructor(private val repository: ReceiverRepository,
private val rxSchedulers: RxSchedulers)
    : BasePresenter<NowPlayingContract.View>(), NowPlayingContract.Presenter {

    override fun subscribe() {
        registerDisposable(subscribeToPlayPosition())
        registerDisposable(subscribeToPlayStateChanges())
        registerDisposable(subscribeToTrackUpdates())
    }

    private fun subscribeToPlayPosition() = repository.getPlayPositionUpdates()
        .observeOn(rxSchedulers.main())
        .subscribe({ playPosition -> view?.updatePosition(playPosition) },
            Timber::w)

    private fun subscribeToTrackUpdates() = repository.getTrackUpdates()
        .observeOn(rxSchedulers.main())
        .subscribe({ track -> view?.updateTrack(track) },
            Timber::w)

    private fun subscribeToPlayStateChanges() = repository.getPlayStateUpdates()
        .observeOn(rxSchedulers.main())
        .subscribe({ playState -> view?.updatePlayState(playState) },
            Timber::w)
}
