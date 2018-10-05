package org.kikermo.thingsaudio.renderer.model

import io.reactivex.Observable
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers

class ReceiverRepositoryImp(private val trackUpdatesObservable: Observable<Track>,
                            private val playStateObservable: Observable<PlayState>,
                            private val playPositionObservable: Observable<Int>,
                            private val rxSchedulers: RxSchedulers
) : ReceiverRepository {

    override fun getTrackUpdates() = trackUpdatesObservable.observeOn(rxSchedulers.main())

    override fun getPlayStateUpdates() = playStateObservable.observeOn(rxSchedulers.main())

    override fun getPlayPositionUpdates() = playPositionObservable.observeOn(rxSchedulers.main())
}
