package org.kikermo.thingsaudio.renderer.service

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import org.kikermo.thingsaudio.core.base.BaseService
import timber.log.Timber

class DiscoveryService : BaseService(), NsdManager.RegistrationListener {

    private val nsdManager: NsdManager by lazy { (getSystemService(Context.NSD_SERVICE) as NsdManager) }

    override fun onCreate() {
        super.onCreate()
        startDiscoveryService()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopDiscoveryServive()
    }

    private fun startDiscoveryService() {
        registerService(8080)
    }

    fun registerService(port: Int) {
        val serviceInfo = NsdServiceInfo().apply {
            serviceName = "Things Audio Renderer"
            serviceType = "_thingsaudio._tcp"
            setPort(port)
        }

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, this@DiscoveryService)
    }

    fun stopDiscoveryServive() {
        nsdManager.unregisterService(this)
    }

    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        Timber.d("Unregistration failed")
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo?) {
        Timber.d("Service unregistered")
    }

    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        Timber.d("Registration failed")
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo?) {
        Timber.d("Registration succeeded")
    }

}