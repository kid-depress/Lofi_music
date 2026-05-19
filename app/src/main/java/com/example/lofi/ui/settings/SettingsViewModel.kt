package com.example.lofi.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lofi.data.local.PreferencesStore
import com.example.lofi.ui.theme.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val prefsStore: PreferencesStore) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = prefsStore.themeMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.LIGHT)

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            prefsStore.setThemeMode(mode)
        }
    }

    class Factory(private val prefsStore: PreferencesStore) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(prefsStore) as T
        }
    }
}
