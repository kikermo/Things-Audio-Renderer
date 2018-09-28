package org.kikermo.thingsaudio.core.base

import android.support.v4.app.Fragment

abstract class BaseFragmentWithPresenter<V : IView, P : IPresenter<V>> : Fragment() {

    abstract fun providePresenter(): P
    abstract fun provideView(): V

    protected val presenter by lazy { providePresenter() }

    override fun onStart() {
        super.onStart()
        presenter.attachView(provideView())
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
        presenter.detachView()
    }
}