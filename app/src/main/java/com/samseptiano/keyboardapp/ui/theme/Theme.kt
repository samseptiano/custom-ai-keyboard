package com.samseptiano.keyboardapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
/**
 * Created by samuel.septiano on 06/04/2025.
 */
private val DarkColorPalette = darkColors(
    primary = Green200,
    primaryVariant = Lime200,
    secondary = Teal200,
    surface = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Lime200,
    primaryVariant = Lime200,
    secondary = Teal200
)

@Composable
fun KeyboardAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}