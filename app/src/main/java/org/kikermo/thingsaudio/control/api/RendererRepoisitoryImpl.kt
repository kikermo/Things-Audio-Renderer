package org.kikermo.thingsaudio.control.api

import io.reactivex.Completable
import io.reactivex.Observable
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import javax.inject.Inject

class RendererRepoisitoryImpl @Inject constructor() : ReceiverRepository {

    override fun getTrackUpdates(): Observable<Track> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayStateUpdates(): Observable<PlayState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayPositionUpdates(): Observable<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendPlayCommand(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendPauseCommand(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendStopCommand(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendSkipNextCommand(songsToSkip: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendSkipPreviousCommand(songsToSkip: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrack(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTrack(track: Track): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearTrackList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrackList(trackList: List<Track>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRepeatMode(repeatMode: RepeatMode): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}