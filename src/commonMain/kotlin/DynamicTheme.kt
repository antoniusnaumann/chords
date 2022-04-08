package dev.antonius.compose.chords

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/*
 * The code in this file is based on existing work,
 * which can be found [here](https://github.com/android/compose-samples/blob/main/Jetchat/app/src/main/java/com/example/compose/jetchat/theme/Themes.kt#L116)
 * and was obtained under the following license:
 *
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @param lightScheme Fallback color scheme if dynamic color is disabled and app is in light mode
 * @param darkScheme Fallback color scheme if dynamic color is disabled and app is in dark mode
 * @param typography Customizable typography settings
 * @param isDarkTheme If a dark theme should be used, conforms to system dark mode by default
 * @param isDynamicColor Whether dynamic color should be used if it is available
 */
@Composable
fun DynamicTheme(
    lightScheme: ColorScheme = lightColorScheme(),
    darkScheme: ColorScheme = darkColorScheme(),
    typography: androidx.compose.material3.Typography = MaterialTheme.typography,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = obtainColorScheme(
            isDynamic = isDynamicColor,
            isDarkTheme = isDarkTheme,
            darkScheme = darkScheme,
            lightScheme = lightScheme,
        ),
        typography = typography
    ) {
        // TODO (M3): MaterialTheme doesn't provide LocalIndication, remove when it does
        val rippleIndication = rememberRipple()
        CompositionLocalProvider(
            LocalIndication provides rippleIndication,
            content = content
        )
    }
}

@Composable
expect fun obtainColorScheme(
    isDynamic: Boolean,
    isDarkTheme: Boolean,
    darkScheme: ColorScheme,
    lightScheme: ColorScheme): ColorScheme