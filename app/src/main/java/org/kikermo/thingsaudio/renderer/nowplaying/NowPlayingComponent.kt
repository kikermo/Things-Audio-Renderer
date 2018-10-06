package org.kikermo.thingsaudio.renderer.nowplaying

import android.support.v4.app.Fragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface NowPlayingComponent: AndroidInjector<Fragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NowPlayingFragment>()

}