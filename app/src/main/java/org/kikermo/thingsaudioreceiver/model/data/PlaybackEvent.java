package org.kikermo.thingsaudioreceiver.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public abstract class PlaybackEvent implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PlaybackEvent() {
    }

    protected PlaybackEvent(Parcel in) {
    }


}
