package org.kikermo.thingsaudioreceiver.model

import android.content.Context
import android.content.IntentFilter

import org.kikermo.thingsaudioreceiver.model.data.PlayPosition
import org.kikermo.thingsaudioreceiver.model.data.PlayState
import org.kikermo.thingsaudioreceiver.model.data.PlaybackEvent
import org.kikermo.thingsaudioreceiver.model.data.Track

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import org.kikermo.thingsaudioreceiver.util.Constants.BA_PLAYBACKEVENT
import org.kikermo.thingsaudioreceiver.util.Constants.BK_PLAYBACKEVENT

class ReceiverRepositoryImp(private val context: Context) : ReceiverRepository {
    //TODO evalueate potential context leak
    private val playbackEventObservable: Observable<PlaybackEvent>

    init {
        this.playbackEventObservable = RxBroadcastReceiver.fromBroadcast(context, IntentFilter(BA_PLAYBACKEVENT))
            .subscribeOn(Schedulers.io())
            .map<Any> { intent -> intent.getSerializableExtra(BK_PLAYBACKEVENT) as PlaybackEvent }
            .share()
    }

    fun subscribeToTrackUpdates(): Observable<Track> {
        return playbackEventObservable
            .filter { playbackEvent -> playbackEvent is Track }
            .cast(Track::class.java)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun subscribeToPlayState(): Observable<PlayState> {
        return playbackEventObservable
            .filter { playbackEvent -> playbackEvent is PlayState }
            .cast(PlayState::class.java)
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun subscribeToPlayPosition(): Observable<PlayPosition> {
        return playbackEventObservable
            .filter { playbackEvent -> playbackEvent is PlayPosition }
            .cast(PlayPosition::class.java)
            .observeOn(AndroidSchedulers.mainThread())
    }
}
