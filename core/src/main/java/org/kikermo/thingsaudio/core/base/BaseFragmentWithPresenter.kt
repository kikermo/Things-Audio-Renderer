package org.kikermo.thingsaudio.core.base

import android.content.Context
import android.support.v4.app.Fragment
import javax.inject.Inject
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragmentWithPresenter<V : IView, P : IPresenter<V>> : Fragment(), IView {

    @Inject
    lateinit var presenter: P

    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
        presenter.detachView()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}