package com.example.lofi.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemeMode { LIGHT, DARK, SYSTEM }

private val LightScheme = lightColorScheme(
    primary = WarmAmber,
    onPrimary = DarkText,
    background = LightBackground,
    onBackground = DarkText,
    surface = LightSurface,
    onSurface = DarkText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = MediumText,
    outline = LightText
)

private val DarkScheme = darkColorScheme(
    primary = WarmAmber,
    onPrimary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkOnBg,
    surface = DarkSurface,
    onSurface = DarkOnBg,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkMediumText,
    outline = DarkLightText
)

@Composable
fun LofiTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    seedColor: Color = WarmAmber,
    content: @Composable () -> Unit
) {
    val useDarkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val scheme = if (useDarkTheme) DarkScheme else LightScheme

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}
