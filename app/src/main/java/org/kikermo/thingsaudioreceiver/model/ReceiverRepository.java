package org.kikermo.thingsaudioreceiver.model;


import org.kikermo.thingsaudioreceiver.model.data.PlayPosition;
import org.kikermo.thingsaudioreceiver.model.data.PlayState;
import org.kikermo.thingsaudioreceiver.model.data.Track;

import io.reactivex.Observable;


/**
 * Created by EnriqueR on 19/02/2017.
 */

public interface ReceiverRepository {

    Observable<Track> subscribeToTrackUpdates();

    Observable<PlayState> subscribeToPlayState();

    Observable<PlayPosition> subscribeToPlayPosition();
}
