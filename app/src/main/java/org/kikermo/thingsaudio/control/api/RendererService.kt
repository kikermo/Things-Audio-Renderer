package org.kikermo.thingsaudio.control.api

import io.reactivex.Completable
import org.kikermo.thingsaudio.core.model.Track
import retrofit2.http.*

interface RendererService {

    @GET("/control/play")
    fun sendPlayCommand(): Completable

    @GET("/control/pause")
    fun sendPauseCommand(): Completable

    @GET("/control/stop")
    fun sendStopCommand(): Completable

    @GET("/control/skip_next/{skip_count}")
    fun sendSkipNextCommand(@Query("skip_count") skipCount: Int): Completable

    @GET("/control/skip_prev/{skip_count}")
    fun sendSkipPreviousCommand(@Query("skip_count") skipCount: Int): Completable

    @POST("/track")
    fun addTrack(@Body track: Track): Completable

    @DELETE("/track")
    fun deleteTrack(@Body track: Track): Completable

    @POST("/tracks")
    fun addTrackList(@Body track: List<Track>): Completable
}