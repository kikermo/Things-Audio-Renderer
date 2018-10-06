package org.kikermo.thingsaudio.renderer.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.kikermo.thingsaudio.renderer.ThingsReceiverApplication

@Component(modules = [AndroidInjectionModule::class,
    AppModule::class,
    FragmentBuilder::class,
    ServiceBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ThingsReceiverApplication)
}