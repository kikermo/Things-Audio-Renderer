package org.kikermo.thingsaudio.core.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxSchedulersImpl @Inject constructor() : RxSchedulers {
    override fun main() = AndroidSchedulers.mainThread()!!

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun trampoline() = Schedulers.trampoline()
}