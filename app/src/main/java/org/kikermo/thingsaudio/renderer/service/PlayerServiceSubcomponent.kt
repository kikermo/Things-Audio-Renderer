package org.kikermo.thingsaudio.renderer.service

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.kikermo.thingsaudio.renderer.di.AppModule

@Subcomponent
interface PlayerServiceSubcomponent : AndroidInjector<PlayerService> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PlayerService>()
}