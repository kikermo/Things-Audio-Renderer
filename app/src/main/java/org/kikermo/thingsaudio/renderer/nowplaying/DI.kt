package org.kikermo.thingsaudio.renderer.nowplaying

import android.content.Context
import org.kikermo.thingsaudio.renderer.ThingsReceiverApplication

fun providePresenter(context: Context): NowPlayingContract.Presenter {

    val repository = (context.applicationContext as ThingsReceiverApplication)
        .receiverRepository

    return NowPlayingPresenter(repository)
}