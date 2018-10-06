package org.kikermo.thingsaudio.core.base

interface IPresenter<T : IView> {

    fun subscribe()

    fun unsubscribe()

    fun attachView(view: T)

    fun detachView()
}
