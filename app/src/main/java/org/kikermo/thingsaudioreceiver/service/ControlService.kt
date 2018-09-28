package org.kikermo.thingsaudioreceiver.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder

import org.kikermo.thingsaudio.core.api.model.Track
import org.kikermo.thingsaudioreceiver.model.net.rest.RestCallback
import org.kikermo.thingsaudioreceiver.model.net.rest.RestServer
import org.kikermo.thingsaudioreceiver.util.Constants

import java.io.Serializable
import java.util.ArrayList

class ControlService : Service(), RestCallback {
    private lateinit var restServer: RestServer

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        startRestServer()
    }

    override fun onDestroy() {
        stopRestServer()
        super.onDestroy()
    }

    private fun startRestServer() {
        restServer = RestServer(8080)
        restServer.setRestCallback(this)
        restServer.start()
    }

    private fun stopRestServer() {
        restServer.stop()
    }

    override fun skipNextReceived() {
        sendBroadcastAction(Constants.BA_SKIP_NEXT, null)
    }

    override fun skipPrevReceived() {
        sendBroadcastAction(Constants.BA_SKIP_PREV, null)
    }

    override fun listReceived(trackList: List<Track>) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.BK_TRACKLIST, trackList as ArrayList<out Serializable>)
        sendBroadcastAction(Constants.BA_NEW_TRACKLIST, bundle)
    }

    override fun playReceived() {
        sendBroadcastAction(Constants.BA_PLAY, null)
    }

    override fun pauseReceived() {
        sendBroadcastAction(Constants.BA_PAUSE, null)
    }

    private fun sendBroadcastAction(action: String, bundle: Bundle?) {
        val intent = Intent()
        intent.action = action
        if (bundle != null)
            intent.putExtras(bundle)
        sendBroadcast(intent)
    }
}
