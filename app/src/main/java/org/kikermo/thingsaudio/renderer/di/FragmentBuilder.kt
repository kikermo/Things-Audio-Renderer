package org.kikermo.thingsaudio.renderer.di

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingFragment

@Module
abstract class FragmentBuilder {
    @Binds
    @IntoMap
    @FragmentKey(NowPlayingFragment::class)
    internal abstract fun bindNowPlayingFragment(builder: NowPlayingComponent.Builder): AndroidInjector.Factory<Fragment>
}