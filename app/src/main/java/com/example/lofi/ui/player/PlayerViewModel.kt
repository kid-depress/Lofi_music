package com.example.lofi.ui.player

import android.app.Application
import android.content.ComponentName
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.lofi.data.model.Station
import com.example.lofi.data.repository.StationRepository
import com.example.lofi.media.RadioMediaService
import com.example.lofi.media.StreamRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.Immutable

@Immutable
data class PlayerUiState(
    val currentStation: Station = StationRepository.allStations.first(),
    val stations: List<Station> = StationRepository.allStations,
    val isPlaying: Boolean = false,
    val isReady: Boolean = false,
    val focusSeconds: Long = 0,
    val sleepTimerSeconds: Long = 0,
    val sleepTimerActive: Boolean = false,
    val volume: Float = 1f
)

@OptIn(UnstableApi::class)
class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    private var mediaController: MediaController? = null
    private var focusJob: Job? = null
    private var sleepJob: Job? = null

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _uiState.update { it.copy(isPlaying = isPlaying) }
            if (isPlaying) startFocusTimer() else stopFocusTimer()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _uiState.update { it.copy(isReady = playbackState == Player.STATE_READY) }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mediaItem?.mediaId?.let { id ->
                StationRepository.getStationById(id)?.let { station ->
                    _uiState.update {
                        it.copy(currentStation = station, focusSeconds = 0)
                    }
                }
            }
        }
    }

    init {
        connectToMediaService()
    }

    private fun connectToMediaService() {
        viewModelScope.launch {
            try {
                val context = getApplication<Application>()
                val sessionToken = SessionToken(
                    context,
                    ComponentName(context, RadioMediaService::class.java)
                )
                val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

                // Block on IO thread to avoid ANR — the main thread must stay free
                // to handle service creation and binding.
                val controller = withContext(Dispatchers.IO) { controllerFuture.get() }
                mediaController = controller
                mediaController?.addListener(playerListener)

                val currentItem = mediaController?.currentMediaItem
                if (currentItem != null && currentItem.mediaId.isNotEmpty()) {
                    val station = StationRepository.getStationById(currentItem.mediaId)
                    if (station != null) {
                        _uiState.update { it.copy(currentStation = station) }
                    }
                }

                _uiState.update {
                    it.copy(
                        isReady = mediaController?.playbackState == Player.STATE_READY,
                        isPlaying = mediaController?.isPlaying ?: false
                    )
                }
            } catch (e: Exception) {
                // Media service not available yet
            }
        }
    }

    fun playStation(station: Station) {
        viewModelScope.launch {
            val controller = mediaController ?: return@launch
            val mediaSource = StreamRouter.createMediaSource(station)
            val mediaItem = MediaItem.Builder()
                .setMediaId(station.id)
                .setUri(station.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(station.name)
                        .setArtist("${station.style1} · ${station.style2}")
                        .build()
                )
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
            _uiState.update {
                it.copy(currentStation = station, focusSeconds = 0)
            }
        }
    }

    fun togglePlayPause() {
        val controller = mediaController ?: return
        if (controller.isPlaying) {
            controller.pause()
        } else {
            if (controller.playbackState == Player.STATE_IDLE) {
                controller.prepare()
            }
            controller.play()
        }
    }

    fun skipToNext() {
        val currentStationId = _uiState.value.currentStation.id
        val currentIndex = StationRepository.getStationIndex(currentStationId)
        val nextIndex = (currentIndex + 1) % _uiState.value.stations.size
        playStation(_uiState.value.stations[nextIndex])
    }

    fun skipToPrevious() {
        val currentStationId = _uiState.value.currentStation.id
        val currentIndex = StationRepository.getStationIndex(currentStationId)
        val prevIndex = if (currentIndex == 0) _uiState.value.stations.size - 1 else currentIndex - 1
        playStation(_uiState.value.stations[prevIndex])
    }

    fun setVolume(volume: Float) {
        _uiState.update { it.copy(volume = volume) }
        mediaController?.setVolume(volume)
    }

    fun setSleepTimer(minutes: Int) {
        sleepJob?.cancel()
        if (minutes <= 0) {
            _uiState.update { it.copy(sleepTimerSeconds = 0, sleepTimerActive = false) }
            return
        }
        val seconds = minutes * 60L
        _uiState.update { it.copy(sleepTimerSeconds = seconds, sleepTimerActive = true) }
        sleepJob = viewModelScope.launch {
            for (remaining in seconds downTo 0) {
                delay(1000)
                _uiState.update { it.copy(sleepTimerSeconds = remaining - 1) }
            }
            mediaController?.pause()
            _uiState.update { it.copy(sleepTimerActive = false) }
        }
    }

    fun cancelSleepTimer() {
        setSleepTimer(0)
    }

    private fun startFocusTimer() {
        focusJob?.cancel()
        focusJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { it.copy(focusSeconds = it.focusSeconds + 1) }
            }
        }
    }

    private fun stopFocusTimer() {
        focusJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        mediaController?.removeListener(playerListener)
        mediaController?.release()
        focusJob?.cancel()
        sleepJob?.cancel()
    }
}
