package org.kikermo.thingsaudio.renderer.service

import android.app.Service
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface PlayerServiceSubcomponent : AndroidInjector<Service> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PlayerService>()
}