package org.kikermo.thingsaudioreceiver.model.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public class Track extends PlaybackEvent {
    private String title;
    private String album;
    private String artist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(artist);
    }

    public Track() {
        super();
    }

    protected Track(Parcel in) {
        super(in);
        this.title = in.readString();
        this.album = in.readString();
        this.artist = in.readString();
    }


    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
