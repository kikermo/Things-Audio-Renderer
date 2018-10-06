package org.kikermo.thingsaudio.renderer.service

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface ControlServiceSubcomponent : AndroidInjector<ControlService> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ControlService>()
}