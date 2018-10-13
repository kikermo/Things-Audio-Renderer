package org.kikermo.thingsaudio.renderer.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import org.kikermo.thingsaudio.renderer.ThingsReceiverApplication
import org.kikermo.thingsaudio.renderer.api.PlayerControlActions
import org.kikermo.thingsaudio.renderer.api.ReceiverRepositoryImp
import javax.inject.Singleton

@Module
class AppModule(val thingsReceiverApplication: ThingsReceiverApplication) {
    @Provides
    @Singleton
    fun provideApp() = thingsReceiverApplication

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideRxScheduler(): RxSchedulers {
        return RxSchedulersImpl()
    }

    @Provides
    @Singleton
    fun providesRepository(trackUpdatesObservable: Observable<Track>,
                           playPositionsObservable: Observable<Int>,
                           playStateObservable: Observable<PlayState>,
                           rxSchedulers: RxSchedulers
    ): ReceiverRepository {
        return ReceiverRepositoryImp(trackUpdatesObservable = trackUpdatesObservable,
            playPositionObservable = playPositionsObservable,
            playStateObservable = playStateObservable,
            rxSchedulers = rxSchedulers)
    }

    @Provides
    @Singleton
    fun provideTrackBehaviourSubject(): BehaviorSubject<Track> = BehaviorSubject.create()

    @Provides
    @Singleton
    fun providePlayPositionSubject(): BehaviorSubject<Int> = BehaviorSubject.create()

    @Provides
    @Singleton
    fun providePlayStateBehaviourSubject(): BehaviorSubject<PlayState> = BehaviorSubject.create()

    @Provides
    @Singleton
    fun providePlayerControlActionsSubject(): PublishSubject<PlayerControlActions> = PublishSubject.create()

    @Provides
    @Singleton
    fun provideTrack(trackBehaviorSubject: BehaviorSubject<Track>): Observable<Track> = trackBehaviorSubject

    @Provides
    @Singleton
    fun providePlayPositionObservable(playPositionBehaviorSubject: BehaviorSubject<Int>): Observable<Int> =
        playPositionBehaviorSubject

    @Provides
    @Singleton
    fun providePlayState(playStateBehaviorSubject: BehaviorSubject<PlayState>): Observable<PlayState> =
        playStateBehaviorSubject

    @Provides
    @Singleton
    fun providePlayerControlActionsObservable(playerControlActionsSubject: PublishSubject<PlayerControlActions>)
        : Observable<PlayerControlActions> = playerControlActionsSubject

    @Provides
    @Singleton
    fun provideRepeatModeBehaviourSubject(): BehaviorSubject<RepeatMode> = BehaviorSubject.createDefault(RepeatMode.DISABLED)

    @Provides
    @Singleton
    fun provideRepeatModeObservable(repeateModeBehaviorSubject: BehaviorSubject<RepeatMode>): Observable<RepeatMode> =
        repeateModeBehaviorSubject

    @Provides
    @Singleton
    fun provideTrackListBehaviourSubject(): BehaviorSubject<List<Track>> = BehaviorSubject.createDefault(listOf())

    @Provides
    @Singleton
    fun provideTrackListObservable(trackListBehaviourSubject: BehaviorSubject<List<Track>>): Observable<List<Track>> =
        trackListBehaviourSubject
}