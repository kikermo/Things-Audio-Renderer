package org.kikermo.thingsaudio.core.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseService : Service() {
    private val compositeDisposable = CompositeDisposable()

    protected fun registerDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not implemented")
    }
}