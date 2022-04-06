package dev.antonius.compose.chords

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

// No dynamic color support for desktop
@Composable
actual fun obtainColorScheme(
    isDynamic: Boolean,
    isDarkTheme: Boolean,
    darkScheme: ColorScheme,
    lightScheme: ColorScheme) = if (isDarkTheme) darkScheme else lightScheme