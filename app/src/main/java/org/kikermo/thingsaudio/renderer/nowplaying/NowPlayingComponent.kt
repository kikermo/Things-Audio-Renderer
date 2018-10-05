package org.kikermo.thingsaudio.renderer.nowplaying

import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Subcomponent
interface NowPlayingComponent: AndroidInjector<Fragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<NowPlayingFragment>()

}