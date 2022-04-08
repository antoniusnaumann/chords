package dev.antonius.compose.chords

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun obtainColorScheme(
    isDynamic: Boolean,
    isDarkTheme: Boolean,
    darkScheme: ColorScheme,
    lightScheme: ColorScheme
): ColorScheme = when {
    isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDarkTheme -> {
        dynamicDarkColorScheme(LocalContext.current)
    }
    isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !isDarkTheme -> {
        dynamicLightColorScheme(LocalContext.current)
    }
    isDarkTheme -> darkScheme
    else -> lightScheme
}