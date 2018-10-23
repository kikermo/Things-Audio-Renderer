package org.kikermo.thingsaudio.renderer.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.control.ControlApplication
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import javax.inject.Singleton

@Module
class AppModule(val controlApplication: ControlApplication) {
    @Provides
    @Singleton
    fun provideApp() = controlApplication

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
                           repeatModeBehaviourSubject: BehaviorSubject<RepeatMode>,
                           rxSchedulers: RxSchedulers
    ): ReceiverRepository {
        return ReceiverRepositoryImp(trackUpdatesObservable = trackUpdatesObservable,
            playPositionObservable = playPositionsObservable,
            playStateObservable = playStateObservable,
            playerControlActionsSubject = playerControlActionsSubject,
            repeatModeBehaviourSubject = repeatModeBehaviourSubject,
            rxSchedulers = rxSchedulers)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}
