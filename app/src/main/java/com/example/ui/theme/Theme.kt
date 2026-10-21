package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkByteColorScheme = darkColorScheme(
    primary = DarkByteBlue,
    onPrimary = DarkByteBg,
    primaryContainer = DarkBytePanel2,
    onPrimaryContainer = DarkByteAccent,
    secondary = DarkByteAccent,
    onSecondary = DarkByteBg,
    background = DarkByteBg,
    onBackground = DarkByteWhite,
    surface = DarkByteSurface,
    onSurface = DarkByteWhite,
    surfaceVariant = DarkBytePanel2,
    onSurfaceVariant = DarkByteMuted,
    outline = DarkByteLine,
    error = DarkByteDanger,
    onError = DarkByteBg
)

@Composable
fun DarkByteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkByteColorScheme,
        typography = Typography,
        content = content
    )
}
