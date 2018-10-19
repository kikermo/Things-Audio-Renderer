package org.kikermo.thingsaudio.renderer.service

import android.os.Build
import com.github.druk.rx2dnssd.BonjourService
import com.github.druk.rx2dnssd.Rx2DnssdEmbedded
import org.kikermo.thingsaudio.core.base.BaseService
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoveryService : BaseService() {

    @Inject lateinit var rxSchedulers: RxSchedulers
    override fun onCreate() {
        super.onCreate()
        startDiscoveryService()
    }

    private fun startDiscoveryService() {
        val rxdnssd = Rx2DnssdEmbedded(this)
        val bs = BonjourService.Builder(0, 0, Build.DEVICE, "_thingsaudio._tcp", null)
            .port(8080)
            .build()

        registerDisposable(rxdnssd.register(bs)
            .subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.main())
            .subscribe({Timber.i("Bonjour Service Successfully registered")}, Timber::e))
    }
}