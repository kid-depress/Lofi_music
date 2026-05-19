package com.example.lofi.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lofi.data.local.PreferencesStore
import com.example.lofi.ui.components.MiniPlayer
import com.example.lofi.ui.discovery.DiscoveryScreen
import com.example.lofi.ui.discovery.DiscoveryViewModel
import com.example.lofi.ui.player.PlayerScreen
import com.example.lofi.ui.player.PlayerViewModel
import com.example.lofi.ui.settings.SettingsScreen
import com.example.lofi.ui.settings.SettingsViewModel
import com.example.lofi.ui.theme.LofiTheme

enum class Screen { Player, Discovery, Settings }

@Composable
fun LofiApp(
    playerViewModel: PlayerViewModel,
    discoveryViewModel: DiscoveryViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val prefsStore = remember { PreferencesStore(context) }
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.Factory(prefsStore)
    )

    val playerState by playerViewModel.uiState.collectAsState()
    val discoveryState by discoveryViewModel.uiState.collectAsState()
    val themeMode by settingsViewModel.themeMode.collectAsState()
    var currentScreen by remember { mutableStateOf(Screen.Player) }

    LofiTheme(themeMode = themeMode) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            bottomBar = {
                if (currentScreen != Screen.Settings) {
                    Column {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            MiniPlayer(
                                station = playerState.currentStation,
                                isPlaying = playerState.isPlaying,
                                onPlayPause = { playerViewModel.togglePlayPause() },
                                onClick = { currentScreen = Screen.Player },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            tonalElevation = 0.dp
                        ) {
                            NavigationBarItem(
                                selected = currentScreen == Screen.Player,
                                onClick = { currentScreen = Screen.Player },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == Screen.Player)
                                            Icons.Filled.MusicNote
                                        else
                                            Icons.Outlined.MusicNote,
                                        contentDescription = "播放"
                                    )
                                },
                                label = { Text("正在播放") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(playerState.currentStation.color.toInt()),
                                    selectedTextColor = Color(playerState.currentStation.color.toInt()),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    indicatorColor = Color.Transparent
                                )
                            )
                            NavigationBarItem(
                                selected = currentScreen == Screen.Discovery,
                                onClick = { currentScreen = Screen.Discovery },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == Screen.Discovery)
                                            Icons.Filled.Search
                                        else
                                            Icons.Outlined.Search,
                                        contentDescription = "发现"
                                    )
                                },
                                label = { Text("发现") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(playerState.currentStation.color.toInt()),
                                    selectedTextColor = Color(playerState.currentStation.color.toInt()),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentScreen) {
                    Screen.Player -> PlayerScreen(
                        currentStation = playerState.currentStation,
                        stations = playerState.stations,
                        isPlaying = playerState.isPlaying,
                        isReady = playerState.isReady,
                        focusSeconds = playerState.focusSeconds,
                        sleepTimerSeconds = playerState.sleepTimerSeconds,
                        sleepTimerActive = playerState.sleepTimerActive,
                        volume = playerState.volume,
                        onPlayPause = { playerViewModel.togglePlayPause() },
                        onSkipNext = { playerViewModel.skipToNext() },
                        onSkipPrevious = { playerViewModel.skipToPrevious() },
                        onVolumeChange = { playerViewModel.setVolume(it) },
                        onSleepTimer = { playerViewModel.setSleepTimer(it) },
                        onStationSelected = { station ->
                            playerViewModel.playStation(station)
                            discoveryViewModel.updateCurrentPlaying(station.id)
                        }
                    )

                    Screen.Discovery -> DiscoveryScreen(
                        categories = discoveryState.categories,
                        selectedCategoryId = discoveryState.selectedCategoryId,
                        stations = discoveryState.filteredStations,
                        currentPlayingId = discoveryState.currentPlayingId,
                        onCategorySelected = { discoveryViewModel.selectCategory(it) },
                        onStationClick = { station ->
                            playerViewModel.playStation(station)
                            discoveryViewModel.updateCurrentPlaying(station.id)
                            currentScreen = Screen.Player
                        },
                        onSettingsClick = { currentScreen = Screen.Settings }
                    )

                    Screen.Settings -> SettingsScreen(
                        themeMode = themeMode,
                        onThemeModeChange = { settingsViewModel.setThemeMode(it) },
                        onBack = { currentScreen = Screen.Discovery }
                    )
                }
            }
        }
    }
}
