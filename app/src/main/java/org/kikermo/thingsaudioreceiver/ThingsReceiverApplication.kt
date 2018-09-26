package org.kikermo.thingsaudioreceiver

import android.app.Application
import android.content.Intent

import org.kikermo.thingsaudioreceiver.service.ControlService
import org.kikermo.thingsaudioreceiver.service.PlayerService

/**
 * Created by EnriqueR on 19/02/2017.
 */

class ThingsReceiverApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startServices()
    }

    override fun onTerminate() {
        stopServices()
        super.onTerminate()
    }

    private fun startServices() {
        var intent = Intent(this, PlayerService::class.java)
        startService(intent)
        intent = Intent(this, ControlService::class.java)
        startService(intent)
    }

    private fun stopServices() {
        var intent = Intent(this, PlayerService::class.java)
        stopService(intent)
        intent = Intent(this, ControlService::class.java)
        stopService(intent)
    }
}
