package org.kikermo.thingsaudio.renderer.di

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingContract
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingFragment
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingPresenter
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingSubcomponent

@Module(subcomponents = [NowPlayingSubcomponent::class])
abstract class FragmentBuilder {
    @Binds
    @IntoMap
    @FragmentKey(NowPlayingFragment::class)
    abstract fun bindNowPlayingFragment(builder: NowPlayingSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    abstract fun contributesNowPlayingPresenter(nowPlayingPresenter: NowPlayingPresenter)
        : NowPlayingContract.Presenter
}