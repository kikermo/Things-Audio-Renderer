package org.kikermo.thingsaudioreceiver.model.net.rest;

import org.kikermo.thingsaudioreceiver.model.data.Track;

import java.util.List;

/**
 * Created by EnriqueR on 28/02/2017.
 */

public interface RestCallback {

   void skipNextReceived();

    void skipPrevReceived();

    void listReceived(List<Track> trackList);

    void playReceived();

    void pauseReceived();
}
