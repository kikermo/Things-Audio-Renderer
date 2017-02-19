package org.kikermo.thingsaudioreceiver.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;


    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
