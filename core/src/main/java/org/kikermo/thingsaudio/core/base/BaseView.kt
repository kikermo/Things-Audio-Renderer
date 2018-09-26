package org.kikermo.thingsaudioreceiver

interface BaseView<T : BasePresenter> {
    fun setBasePresenter(presenter: T)
}
