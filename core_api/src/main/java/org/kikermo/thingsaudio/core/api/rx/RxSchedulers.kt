package org.kikermo.thingsaudio.core.api.rx

import io.reactivex.Scheduler

interface RxSchedulers {

    fun main(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler
}