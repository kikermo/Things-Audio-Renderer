package org.kikermo.thingsaudio.core.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : IView> : IPresenter<V> {
    protected var view: V? = null

    private val disposables = CompositeDisposable()

    override fun unsubscribe() {
        disposables.clear()
    }

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun registerDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
