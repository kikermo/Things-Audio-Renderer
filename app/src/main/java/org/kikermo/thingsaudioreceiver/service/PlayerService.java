package org.kikermo.thingsaudioreceiver.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer mediaPlayer;
    private List<Track> trackList;
    private int trackPointer;
    private boolean repeatList;
    private boolean repeatSong;


    public PlayerService() {
        trackList = new ArrayList<>();
        trackPointer = 0;
        repeatList = false;
        repeatSong = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BA_NEW_TRACKLIST);
        intentFilter.addAction(Constants.BA_SKIP_NEXT);
        intentFilter.addAction(Constants.BA_SKIP_PREV);
        intentFilter.addAction(Constants.BA_PAUSE);
        intentFilter.addAction(Constants.BA_PLAY);
        registerReceiver(controlReceiver, intentFilter);
    }


    @Override
    public void onDestroy() {
        mediaPlayer.release();
        mediaPlayer = null;
        unregisterReceiver(controlReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (repeatSong) {
            mediaPlayer.start();
            return;
        }

        if (trackPointer >= trackList.size() + 1) {
            trackPointer++;
            playCurrentSong();
        } else {
            trackPointer = 0;
            if (repeatList)
                playCurrentSong();
        }
    }

    private void playCurrentSong() {
        Track track = trackList.get(trackPointer);

        Intent intent = new Intent();
        intent.putExtra(Constants.BK_PLAYBACKEVENT, track);
        intent.setAction(Constants.BA_PLAYBACKEVENT);

        sendBroadcast(intent);
        try {
            mediaPlayer.setDataSource(track.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BA_PAUSE:
                    mediaPlayer.pause();
                    break;
                case Constants.BA_PLAY:
                    mediaPlayer.start();
                    break;
                case Constants.BA_SKIP_NEXT:
                    if (trackPointer >= trackList.size() + 1) {
                        trackPointer++;
                        playCurrentSong();
                    }
                    break;
                case Constants.BA_SKIP_PREV:
                    if (trackPointer <= 1) {
                        trackPointer--;
                        playCurrentSong();
                    }
                    break;
                case Constants.BA_NEW_TRACKLIST:
                    List<Track> newTracks = intent.getParcelableArrayListExtra(Constants.BA_NEW_TRACKLIST);
                    mediaPlayer.stop();
                    trackList.clear();
                    trackList.addAll(newTracks);
                    trackPointer = 0;
                    playCurrentSong();
                    break;
            }
        }
    };
}
