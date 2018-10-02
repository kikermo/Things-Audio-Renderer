package org.kikermo.thingsaudioreceiver.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import org.kikermo.thingsaudio.core.rx.RxSchedulersImpl
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
}