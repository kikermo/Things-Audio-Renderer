package org.kikermo.thingsaudio.renderer.service

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface PlayerServiceSubcomponent : AndroidInjector<PlayerService> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PlayerService>()
}