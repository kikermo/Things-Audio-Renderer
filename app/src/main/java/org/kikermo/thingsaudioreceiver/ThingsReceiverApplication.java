package org.kikermo.thingsaudioreceiver;

import android.app.Application;
import android.content.Intent;

import org.kikermo.thingsaudioreceiver.service.ControlService;
import org.kikermo.thingsaudioreceiver.service.PlayerService;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public class ThingsReceiverApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        startServices();
    }

    @Override
    public void onTerminate() {
        stopServices();
        super.onTerminate();
    }

    private void startServices() {
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
        intent = new Intent(this, ControlService.class);
        startService(intent);
    }

    private void stopServices(){
        Intent intent = new Intent(this, PlayerService.class);
        stopService(intent);
        intent = new Intent(this, ControlService.class);
        stopService(intent);
    }
}
