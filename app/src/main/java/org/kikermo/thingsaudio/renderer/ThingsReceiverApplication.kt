package org.kikermo.thingsaudio.renderer

import android.app.Application
import android.app.Service
import android.content.Intent
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import org.kikermo.thingsaudio.renderer.di.AppModule
import org.kikermo.thingsaudio.renderer.service.ControlService
import org.kikermo.thingsaudio.renderer.service.PlayerService
import org.kikermo.thingsaudioreceiver.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class ThingsReceiverApplication : Application(), HasSupportFragmentInjector, HasServiceInjector {

    // region manual DI section
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun serviceInjector() = serviceInjector

    //endregion

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .appModule(AppModule(this))
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
