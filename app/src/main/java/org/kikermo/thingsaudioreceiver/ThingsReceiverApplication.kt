package org.kikermo.thingsaudioreceiver

import android.app.Application
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudioreceiver.di.DaggerAppComponent
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import org.kikermo.thingsaudioreceiver.model.PlayerControlActions
import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp
import org.kikermo.thingsaudioreceiver.service.ControlService
import org.kikermo.thingsaudioreceiver.service.PlayerService
import timber.log.Timber

class ThingsReceiverApplication : Application() {

    // region manual DI section

    val trackSubject: BehaviorSubject<Track> = BehaviorSubject.create()
    val playPositionsSubject: BehaviorSubject<Int> = BehaviorSubject.create()
    val playState: BehaviorSubject<PlayState> = BehaviorSubject.create()

    val controlSubject: PublishSubject<PlayerControlActions> = PublishSubject.create()

    private val rxSchedulers = RxSchedulersImpl()

    val receiverRepository by lazy {
        ReceiverRepositoryImp(trackUpdatesObservable = trackSubject,
            playPositionObservable = playPositionsSubject,
            playStateObservable = playState,
            rxSchedulers = rxSchedulers)
    }

    val playerControlsObservable: Observable<PlayerControlActions> = controlSubject

    //endregion

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        startServices()
        initTimber()
    }

    private fun startServices() {
        var intent = Intent(this, PlayerService::class.java)
        startService(intent)
        intent = Intent(this, ControlService::class.java)
        startService(intent)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
