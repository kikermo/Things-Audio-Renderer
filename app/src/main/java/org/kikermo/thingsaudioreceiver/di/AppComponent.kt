package org.kikermo.thingsaudioreceiver.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.kikermo.thingsaudioreceiver.ThingsReceiverApplication

//@Component(modules = [AndroidInjectionModule::class,
//    AppModule::class,
//    FragmentBuilder::class])
@Component
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ThingsReceiverApplication)
}