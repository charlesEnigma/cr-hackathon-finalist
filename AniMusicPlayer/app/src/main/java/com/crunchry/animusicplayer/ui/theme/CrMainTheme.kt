package com.crunchry.animusicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CrColors.Brand.Orange,
    background = CrColors.Neutral.Base, // unified near-black
    surface = CrColors.Neutral.Base,
    onPrimary = CrColors.Neutral.White,
    onBackground = CrColors.Neutral.White,
    onSurface = CrColors.Neutral.White,
)

@Composable
fun CrMainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
