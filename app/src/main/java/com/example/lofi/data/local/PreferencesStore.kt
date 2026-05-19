package com.example.lofi.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lofi.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lofi_prefs")

class PreferencesStore(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorites")
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        private val SEED_COLOR_KEY = intPreferencesKey("seed_color")
    }

    val favoriteIds: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[FAVORITES_KEY] ?: emptySet()
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        when (prefs[THEME_MODE_KEY]) {
            "dark" -> ThemeMode.DARK
            "system" -> ThemeMode.SYSTEM
            else -> ThemeMode.LIGHT
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = when (mode) {
                ThemeMode.LIGHT -> "light"
                ThemeMode.DARK -> "dark"
                ThemeMode.SYSTEM -> "system"
            }
        }
    }

    suspend fun toggleFavorite(stationId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(stationId)) {
                current.remove(stationId)
            } else {
                current.add(stationId)
            }
            prefs[FAVORITES_KEY] = current
        }
    }

    suspend fun isFavorite(stationId: String): Boolean {
        val favs = context.dataStore.data.map { it[FAVORITES_KEY] ?: emptySet() }
        var result = false
        favs.collect { result = it.contains(stationId); return@collect }
        return result
    }
}
