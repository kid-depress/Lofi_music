package com.example.lofi.ui.player

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.MaterialTheme

@Composable
fun VinylDisc(
    isPlaying: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier,
    size: Float = 280f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (isPlaying) 40_000 else Int.MAX_VALUE,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "vinyl_rotate"
    )

    val discColor = Color(0xFF1E1E28)
    val grooveColor = Color(0xFF30303A)
    val labelColor = accentColor
    val centerColor = MaterialTheme.colorScheme.background

    Canvas(
        modifier = modifier.rotate(if (isPlaying) rotation else 0f)
    ) {
        val halfSize = size / 2f
        val radius = size / 2f
        val center = Offset(halfSize, halfSize)

        // Outer shadow ring
        drawCircle(
            color = Color.Black.copy(alpha = 0.3f),
            radius = radius,
            center = center.copy(y = center.y + 4f)
        )

        // Main disc
        drawCircle(color = discColor, radius = radius, center = center)

        // Grooves
        for (i in 1..8) {
            val grooveRadius = radius * (0.75f - i * 0.07f)
            drawCircle(
                color = grooveColor,
                radius = grooveRadius,
                center = center,
                style = Stroke(width = 1f)
            )
        }

        // Colored label
        val labelRadius = radius * 0.32f
        drawCircle(color = labelColor.copy(alpha = 0.6f), radius = labelRadius, center = center)
        drawCircle(
            color = labelColor,
            radius = labelRadius,
            center = center,
            style = Stroke(width = 2f)
        )

        // Inner ring
        drawCircle(
            color = discColor,
            radius = labelRadius * 0.75f,
            center = center
        )

        // Center hole
        drawCircle(color = centerColor, radius = 6f, center = center)

        // Shine highlight
        drawCircle(
            color = Color.White.copy(alpha = 0.03f),
            radius = radius * 0.6f,
            center = Offset(center.x - radius * 0.2f, center.y - radius * 0.2f)
        )
    }
}
