package com.example.lofi.ui.discovery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lofi.data.local.PreferencesStore
import com.example.lofi.data.model.Category
import com.example.lofi.data.model.Station
import com.example.lofi.data.repository.StationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DiscoveryUiState(
    val categories: List<Category> = StationRepository.categories,
    val selectedCategoryId: String = "all",
    val filteredStations: List<Station> = StationRepository.allStations,
    val favoriteIds: Set<String> = emptySet(),
    val currentPlayingId: String? = null
)

class DiscoveryViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(DiscoveryUiState())
    val uiState: StateFlow<DiscoveryUiState> = _uiState.asStateFlow()

    private val prefsStore = PreferencesStore(application)

    fun selectCategory(categoryId: String) {
        val filtered = StationRepository.getFilteredStations(categoryId)
        _uiState.update {
            it.copy(
                selectedCategoryId = categoryId,
                filteredStations = filtered
            )
        }
    }

    fun toggleFavorite(stationId: String) {
        val newFavs = _uiState.value.favoriteIds.toMutableSet()
        if (newFavs.contains(stationId)) {
            newFavs.remove(stationId)
        } else {
            newFavs.add(stationId)
        }
        _uiState.update { it.copy(favoriteIds = newFavs) }
    }

    fun updateCurrentPlaying(stationId: String?) {
        _uiState.update { it.copy(currentPlayingId = stationId) }
    }
}
