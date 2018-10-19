package org.kikermo.thingsaudio.renderer.api

import io.reactivex.Completable.fromCallable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import javax.inject.Inject

class ReceiverRepositoryImp @Inject constructor(
    private val trackUpdatesObservable: Observable<Track>,
    private val playStateObservable: Observable<PlayState>,
    private val playPositionObservable: Observable<Int>,
    private var playerControlActionsSubject: PublishSubject<PlayerControlActions>,
    private val rxSchedulers: RxSchedulers
) : ReceiverRepository {
    override fun sendPauseCommand() = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.Pause)
    }

    override fun sendStopCommand() = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.Stop)
    }

    override fun sendPlayCommand() = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.Play)
    }

    override fun sendSkipNextCommand(songsToSkip: Int) = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.SkipNext(songsToSkip))
    }

    override fun sendSkipPreviousCommand(songsToSkip: Int) = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.SkipPrevious(songsToSkip))
    }

    override fun addTrack(track: Track) = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrack(track))
    }

    override fun deleteTrack(track: Track) = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.DeleteTrack(track))
    }

    override fun clearTrackList() = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.ClearTackList)
    }

    override fun addTrackList(trackList: List<Track>) = fromCallable {
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrackList(trackList))
    }

    override fun getTrackUpdates() = trackUpdatesObservable.observeOn(rxSchedulers.main())

    override fun getPlayStateUpdates() = playStateObservable.observeOn(rxSchedulers.main())

    override fun getPlayPositionUpdates() = playPositionObservable.observeOn(rxSchedulers.main())
}
