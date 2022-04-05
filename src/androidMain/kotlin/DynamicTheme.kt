package dev.antonius.compose.chords

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun obtainColorScheme(
    isDynamic: Boolean,
    isDarkTheme: Boolean,
    darkScheme: ColorScheme,
    lightScheme: ColorScheme
): ColorScheme = quickFixTertiary(when {
    isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && isDarkTheme -> {
        dynamicDarkColorScheme(LocalContext.current)
    }
    isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !isDarkTheme -> {
        dynamicLightColorScheme(LocalContext.current)
    }
    isDarkTheme -> darkScheme
    else -> lightScheme
}, isDynamic, isDarkTheme)

@Deprecated("This is a quick fix, because dynamic color schemes currently do not provide a tertiary color. Remove this once this is fixed.")
@Composable
private fun quickFixTertiary(brokenScheme: ColorScheme, isDynamic: Boolean, isDarkTheme: Boolean) =
    if (isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val res = LocalContext.current.resources
        val theme = LocalContext.current.theme

        val (tertiaryId, onTertiaryId) = if (isDarkTheme) {
            // Dark: Tertiary 80, OnTertiary 20
            android.R.color.system_accent3_200 to android.R.color.system_accent3_800
        } else {
            // Light: Tertiary 40, OnTertiary 100
            android.R.color.system_accent3_600 to android.R.color.system_accent3_0
        }

        val tertiary = Color(res.getColor(tertiaryId, theme))
        val onTertiary = Color(res.getColor(onTertiaryId, theme))

        brokenScheme.copy(tertiary = tertiary, onTertiary = onTertiary)
    } else brokenScheme