package org.kikermo.thingsaudio.renderer.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import java.util.concurrent.Callable
import javax.inject.Inject

class ReceiverRepositoryImp @Inject constructor(
    private val trackUpdatesObservable: Observable<Track>,
    private val playStateObservable: Observable<PlayState>,
    private val playPositionObservable: Observable<Int>,
    private val playerControlActionsSubject: PublishSubject<PlayerControlActions>,
    private val repeatModeBehaviourSubject: BehaviorSubject<RepeatMode>,
    private val rxSchedulers: RxSchedulers
) : ReceiverRepository {
    override fun setRepeatMode(repeatMode: RepeatMode) = createCompletable(Callable { repeatModeBehaviourSubject.onNext(repeatMode) })

    override fun sendPauseCommand() = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.Pause)
    })

    override fun sendStopCommand() = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.Stop)
    })

    override fun sendPlayCommand() = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.Play)
    })

    override fun sendSkipNextCommand(songsToSkip: Int) = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.SkipNext(songsToSkip))
    })

    override fun sendSkipPreviousCommand(songsToSkip: Int) = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.SkipPrevious(songsToSkip))
    })

    override fun addTrack(track: Track) = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrack(track))
    })

    override fun deleteTrack(track: Track) = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.DeleteTrack(track))
    })

    override fun clearTrackList() = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.ClearTackList)
    })

    override fun addTrackList(trackList: List<Track>) = createCompletable(Callable {
        playerControlActionsSubject.onNext(PlayerControlActions.AddTrackList(trackList))
    })

    override fun getTrackUpdates() = trackUpdatesObservable.subscribeOn(rxSchedulers.io())

    override fun getPlayStateUpdates() = playStateObservable.subscribeOn(rxSchedulers.io())

    override fun getPlayPositionUpdates() = playPositionObservable.subscribeOn(rxSchedulers.io())

    private fun createCompletable(callable: Callable<*>): Completable {
        return Completable.fromCallable(callable)
            .subscribeOn(rxSchedulers.io())
    }
}
