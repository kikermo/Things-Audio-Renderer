package org.kikermo.thingsaudio.control.api

import io.reactivex.Completable
import org.kikermo.thingsaudio.core.api.ReceiverRepository
import org.kikermo.thingsaudio.core.model.RepeatMode
import org.kikermo.thingsaudio.core.model.Track
import org.kikermo.thingsaudio.core.rx.RxSchedulers
import javax.inject.Inject

class RendererRepoisitoryImpl @Inject constructor(
    private val rendererService: RendererService,
    private val rendererUpdatesService: RendererUpdatesService,
    private val rxSchedulers: RxSchedulers
) : ReceiverRepository {

    override fun getTrackUpdates() = rendererUpdatesService.getTrackUpdates().subscribeOn(rxSchedulers.io())

    override fun getPlayStateUpdates() = rendererUpdatesService.getPlayStateUpdates().subscribeOn(rxSchedulers.io())

    override fun getPlayPositionUpdates() = rendererUpdatesService.getPlayPositionUpdates().subscribeOn(rxSchedulers.io())

    override fun sendPlayCommand() = rendererService.sendPlayCommand().subscribeOn(rxSchedulers.io())

    override fun sendPauseCommand() = rendererService.sendPauseCommand().subscribeOn(rxSchedulers.io())

    override fun sendStopCommand() = rendererService.sendStopCommand().subscribeOn(rxSchedulers.io())

    override fun sendSkipNextCommand(songsToSkip: Int) = rendererService
        .sendSkipNextCommand(songsToSkip)
        .subscribeOn(rxSchedulers.io())

    override fun sendSkipPreviousCommand(songsToSkip: Int) = rendererService
        .sendSkipPreviousCommand(songsToSkip)
        .subscribeOn(rxSchedulers.io())

    override fun addTrack(track: Track) = rendererService.addTrack(track)
        .subscribeOn(rxSchedulers.io())

    override fun deleteTrack(track: Track) = rendererService.deleteTrack(track)
        .subscribeOn(rxSchedulers.io())

    override fun clearTrackList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTrackList(trackList: List<Track>) = rendererService.addTrackList(trackList)
        .subscribeOn(rxSchedulers.io())

    override fun setRepeatMode(repeatMode: RepeatMode): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}