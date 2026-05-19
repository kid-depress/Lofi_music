package com.example.lofi.media

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.lofi.data.model.Station
import com.example.lofi.data.model.StreamType

object StreamRouter {

    private val dataSourceFactory = DefaultHttpDataSource.Factory()
        .setAllowCrossProtocolRedirects(true)
        .setUserAgent("Mozilla/5.0 (compatible; LofiRadio/1.0)")

    fun createMediaItem(station: Station): MediaItem =
        MediaItem.Builder()
            .setMediaId(station.id)
            .setUri(station.url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(station.name)
                    .setArtist("${station.style1} · ${station.style2}")
                    .build()
            )
            .build()

    fun createMediaSource(station: Station): MediaSource = when (station.type) {
        StreamType.MP3 -> ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(station.url))

        StreamType.M3U8 -> HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(station.url))
    }
}
