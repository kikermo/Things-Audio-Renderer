package org.kikermo.thingsaudioreceiver

import android.app.Application
import android.content.Intent

import org.kikermo.thingsaudioreceiver.service.ControlService
import org.kikermo.thingsaudioreceiver.service.PlayerService

class ThingsReceiverApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startServices()
    }

    private fun startServices() {
        var intent = Intent(this, PlayerService::class.java)
        startService(intent)
        intent = Intent(this, ControlService::class.java)
        startService(intent)
    }
}
