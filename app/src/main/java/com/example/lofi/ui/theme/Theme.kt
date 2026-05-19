package com.example.lofi.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ThemeMode { LIGHT, DARK, SYSTEM }

private val LightScheme = lightColorScheme(
    primary = WarmAmber,
    secondary = WarmRose,
    tertiary = WarmTeal,
    onPrimary = DarkText,
    background = LightBackground,
    onBackground = DarkText,
    surface = LightSurface,
    onSurface = DarkText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = MediumText,
    outline = LightText,
    outlineVariant = LightSurfaceVariant
)

private val DarkScheme = darkColorScheme(
    primary = WarmAmber,
    secondary = WarmRose,
    tertiary = WarmTeal,
    onPrimary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkOnBg,
    surface = DarkSurface,
    onSurface = DarkOnBg,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkMediumText,
    outline = DarkLightText,
    outlineVariant = DarkSurfaceVariant
)

private val LofiShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(34.dp)
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
        shapes = LofiShapes,
        content = content
    )
}
