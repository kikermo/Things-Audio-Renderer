package org.kikermo.thingsaudioreceiver.nowplaying

import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.base.BasePresenter
import timber.log.Timber

class NowPlayingPresenter(private val repository: ReceiverRepository) : BasePresenter<NowPlayingContract.View>(), NowPlayingContract.Presenter {

    override fun subscribe() {
        registerDisposable(subscribeToPlayPosition())
        registerDisposable(subscribeToPlayStateChanges())
        registerDisposable(subscribeToTrackUpdates())
    }

    private fun subscribeToPlayPosition() = repository.getPlayPositionUpdates()
        .subscribe({ playPosition -> view?.updatePosition(playPosition) },
            Timber::w)

    private fun subscribeToTrackUpdates() = repository.getTrackUpdates()
        .subscribe({ track -> view?.updateTrack(track) },
            Timber::w)

    private fun subscribeToPlayStateChanges() = repository.getPlayStateUpdates()
        .subscribe({ playState -> view?.updatePlayState(playState) },
            Timber::w)
}
