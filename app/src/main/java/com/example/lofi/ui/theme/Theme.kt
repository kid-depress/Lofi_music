package com.example.lofi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LofiColorScheme = darkColorScheme(
    primary = White90,
    onPrimary = DarkBackground,
    background = DarkBackground,
    onBackground = White90,
    surface = DarkSurface,
    onSurface = White90,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = White60,
    outline = White30
)

@Composable
fun LofiTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LofiColorScheme,
        typography = Typography,
        content = content
    )
}
