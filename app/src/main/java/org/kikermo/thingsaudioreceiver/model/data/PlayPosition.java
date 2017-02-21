package org.kikermo.thingsaudioreceiver.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.kikermo.thingsaudioreceiver.util.Utils;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public class PlayPosition extends PlaybackEvent {
    private int position; //Position in secconds

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFormattedPosition() {
        return Utils.formatSeconds(position);
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(position);
    }

    public PlayPosition() {
        super();
    }

    protected PlayPosition(Parcel in) {
        super(in);
        this.position = in.readInt();
    }


    public static final Parcelable.Creator<PlayPosition> CREATOR = new Parcelable.Creator<PlayPosition>() {
        @Override
        public PlayPosition createFromParcel(Parcel source) {
            return new PlayPosition(source);
        }

        @Override
        public PlayPosition[] newArray(int size) {
            return new PlayPosition[size];
        }
    };
}
