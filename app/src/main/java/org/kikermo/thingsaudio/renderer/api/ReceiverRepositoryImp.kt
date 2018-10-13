package org.kikermo.thingsaudio.renderer.api

import io.reactivex.Completable
import io.reactivex.Observable
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import javax.inject.Inject

class ReceiverRepositoryImp @Inject constructor(
    private val trackUpdatesObservable: Observable<Track>,
    private val playStateObservable: Observable<PlayState>,
    private val playPositionObservable: Observable<Int>,
    private val rxSchedulers: RxSchedulers
) : ReceiverRepository {

    override fun getVolumeUpdates(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVolume(volume: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendPlayCommand(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendSkipNextCommand(songsToSkip: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendSkipPreviousCommand(songsToSkip: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrack(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTrack(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearTrackList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrackList(trackList: List<Track>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTrackUpdates() = trackUpdatesObservable.observeOn(rxSchedulers.main())

    override fun getPlayStateUpdates() = playStateObservable.observeOn(rxSchedulers.main())

    override fun getPlayPositionUpdates() = playPositionObservable.observeOn(rxSchedulers.main())
}
