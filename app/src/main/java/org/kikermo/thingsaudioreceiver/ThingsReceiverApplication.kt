package org.kikermo.thingsaudioreceiver

import android.app.Application
import android.content.Intent
import io.reactivex.subjects.BehaviorSubject
import org.kikermo.thingsaudio.core.api.model.PlayState
import org.kikermo.thingsaudio.core.api.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp
import org.kikermo.thingsaudioreceiver.service.ControlService
import org.kikermo.thingsaudioreceiver.service.PlayerService
import timber.log.Timber

class ThingsReceiverApplication : Application() {

    private val trackSubject: BehaviorSubject<Track> = BehaviorSubject.create()
    private val playPositionsSubject: BehaviorSubject<Int> = BehaviorSubject.create()
    private val playState: BehaviorSubject<PlayState> = BehaviorSubject.create()

    private val rxSchedulers = RxSchedulersImpl()

    val receiverRepository by lazy {
        ReceiverRepositoryImp(trackUpdatesObservable = trackSubject,
            playPositionObservable = playPositionsSubject,
            playStateObservable = playState,
            rxSchedulers = rxSchedulers)
    }

    override fun onCreate() {
        super.onCreate()
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
