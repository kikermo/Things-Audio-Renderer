package org.kikermo.thingsaudio.renderer.di

import android.app.Service
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ServiceKey
import dagger.multibindings.IntoMap
import org.kikermo.thingsaudio.renderer.service.ControlService
import org.kikermo.thingsaudio.renderer.service.ControlServiceSubcomponent
import org.kikermo.thingsaudio.renderer.service.PlayerService
import org.kikermo.thingsaudio.renderer.service.PlayerServiceSubcomponent

@Module
abstract class ServiceBuilder {
    @Binds
    @IntoMap
    @ServiceKey(PlayerService::class)
    internal abstract fun bindPlayerService(builder: PlayerServiceSubcomponent.Builder): AndroidInjector.Factory<Service>

    @Binds
    @IntoMap
    @ServiceKey(ControlService::class)
    internal abstract fun bindControlService(builder: ControlServiceSubcomponent.Builder): AndroidInjector.Factory<Service>
}