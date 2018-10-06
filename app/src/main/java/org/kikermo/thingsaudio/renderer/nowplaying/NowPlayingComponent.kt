package org.kikermo.thingsaudio.renderer.nowplaying

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.kikermo.thingsaudio.renderer.di.AppComponent

@Subcomponent(modules = [AppComponent::class])
interface NowPlayingComponent : AndroidInjector<NowPlayingFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NowPlayingFragment>()
}