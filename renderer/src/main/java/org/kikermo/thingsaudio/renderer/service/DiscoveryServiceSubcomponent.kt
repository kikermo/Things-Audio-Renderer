package org.kikermo.thingsaudio.renderer.service

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface DiscoveryServiceSubcomponent : AndroidInjector<DiscoveryService> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<DiscoveryService>()
}