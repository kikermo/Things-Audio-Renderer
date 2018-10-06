package org.kikermo.thingsaudio.renderer.service

import android.app.Service
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface ControlServiceSubcomponent : AndroidInjector<Service> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ControlService>()
}