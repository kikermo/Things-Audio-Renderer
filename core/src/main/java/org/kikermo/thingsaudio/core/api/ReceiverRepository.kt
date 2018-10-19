package org.kikermo.thingsaudio.core.api

import io.reactivex.Completable
import io.reactivex.Observable
import org.kikermo.thingsaudio.core.model.PlayState
import org.kikermo.thingsaudio.core.model.Track

interface ReceiverRepository {

    fun getTrackUpdates(): Observable<Track>

    fun getPlayStateUpdates(): Observable<PlayState>

    fun getPlayPositionUpdates(): Observable<Int>

    fun sendPlayCommand(): Completable

    fun sendPauseCommand(): Completable

    fun sendStopCommand(): Completable

    fun sendSkipNextCommand(songsToSkip: Int = 1): Completable

    fun sendSkipPreviousCommand(songsToSkip: Int = 1): Completable

    fun addTrack(track: Track): Completable

    fun deleteTrack(track: Track): Completable

    fun clearTrackList(): Completable

    fun addTrackList(trackList: List<Track>): Completable
}
