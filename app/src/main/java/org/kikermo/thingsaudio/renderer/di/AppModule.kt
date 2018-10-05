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
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import org.kikermo.thingsaudio.renderer.model.PlayerControlActions
import org.kikermo.thingsaudio.renderer.model.ReceiverRepositoryImp
import javax.inject.Singleton

@Module
abstract class AppModule {

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
    fun providePlayerControlActions(playerControlActionsSubject: PublishSubject<PlayerControlActions>)
        : Observable<PlayerControlActions> = playerControlActionsSubject
}