package org.kikermo.thingsaudioreceiver.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.model.net.rest.RestCallback;
import org.kikermo.thingsaudioreceiver.model.net.rest.RestServer;
import org.kikermo.thingsaudioreceiver.util.Constants;
import org.kikermo.thingsaudioreceiver.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ControlService extends Service implements RestCallback {
    private RestServer restServer;

    public ControlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startRestServer();
    }

    @Override
    public void onDestroy() {
        stopRestServer();
        super.onDestroy();

    }

    private void startRestServer() {
        restServer = new RestServer(8080);
        restServer.setRestCallback(this);
        restServer.start();
    }

    private void stopRestServer() {
        restServer.stop();
    }

    @Override
    public void skipNextReceived() {
        sendBroadcastAction(Constants.BA_SKIP_NEXT, null);
    }

    @Override
    public void skipPrevReceived() {
        sendBroadcastAction(Constants.BA_SKIP_PREV, null);
    }

    @Override
    public void listReceived(List<Track> trackList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.BK_TRACKLIST, (ArrayList<? extends Parcelable>) trackList);
        sendBroadcastAction(Constants.BA_NEW_TRACKLIST, bundle);
    }

    @Override
    public void playReceived() {
        sendBroadcastAction(Constants.BA_PLAY, null);
    }

    @Override
    public void pauseReceived() {
        sendBroadcastAction(Constants.BA_PAUSE, null);
    }

    private void sendBroadcastAction(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null)
            intent.putExtras(bundle);
        sendBroadcast(intent);
    }
}
