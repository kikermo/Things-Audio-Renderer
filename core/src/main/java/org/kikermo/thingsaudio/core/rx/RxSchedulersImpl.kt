package org.kikermo.thingsaudio.core.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kikermo.thingsaudio.core.api.rx.RxSchedulers

class RxSchedulersImpl : RxSchedulers {
    override fun main() = AndroidSchedulers.mainThread()

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun trampoline() = Schedulers.trampoline()
}