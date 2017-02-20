package org.kikermo.thingsaudioreceiver.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public class PlayState extends PlaybackEvent {
    public static final int STATE_READY = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;

    private int state;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(state);
    }

    public PlayState() {
        super();
    }

    protected PlayState(Parcel in) {
        super(in);
        this.state = in.readInt();
    }


    public static final Parcelable.Creator<PlayState> CREATOR = new Parcelable.Creator<PlayState>() {
        @Override
        public PlayState createFromParcel(Parcel source) {
            return new PlayState(source);
        }

        @Override
        public PlayState[] newArray(int size) {
            return new PlayState[size];
        }
    };
}
