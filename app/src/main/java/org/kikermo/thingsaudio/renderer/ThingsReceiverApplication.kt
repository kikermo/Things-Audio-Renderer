package org.kikermo.thingsaudio.renderer

import android.app.Activity
import android.app.Application
import android.content.Intent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudioreceiver.di.DaggerAppComponent
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
import org.kikermo.thingsaudio.renderer.model.PlayerControlActions
import org.kikermo.thingsaudio.renderer.model.ReceiverRepositoryImp
import org.kikermo.thingsaudio.renderer.service.ControlService
import org.kikermo.thingsaudio.renderer.service.PlayerService
import timber.log.Timber
import javax.inject.Inject

class ThingsReceiverApplication : Application(), HasSupportFragmentInjector {

    // region manual DI section
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>



    private val rxSchedulers = RxSchedulersImpl()

    val receiverRepository by lazy {
        ReceiverRepositoryImp(trackUpdatesObservable = trackSubject,
            playPositionObservable = playPositionsSubject,
            playStateObservable = playState,
            rxSchedulers = rxSchedulers)
    }

    val playerControlsObservable: Observable<PlayerControlActions> = controlSubject

    override fun supportFragmentInjector() = fragmentInjector

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
