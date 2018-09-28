package org.kikermo.thingsaudioreceiver.nowplaying

import android.content.Context
import org.kikermo.thingsaudioreceiver.ThingsReceiverApplication

fun providePresenter(context: Context): NowPlayingContract.Presenter {

    val repository = (context.applicationContext as ThingsReceiverApplication)
        .receiverRepository

    return NowPlayingPresenter(repository)
}