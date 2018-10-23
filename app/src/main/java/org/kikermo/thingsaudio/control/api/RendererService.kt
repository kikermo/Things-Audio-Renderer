package org.kikermo.thingsaudio.control.api

import io.reactivex.Completable

interface RendererService {

    @GET("/control/play")
    fun sendPlay() : Completable
}