package org.kikermo.thingsaudio.renderer.nowplaying

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface NowPlayingSubcomponent : AndroidInjector<NowPlayingFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NowPlayingFragment>()
}