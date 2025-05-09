package com.samseptiano.keyboardapp.utils

import androidx.compose.ui.graphics.Color

/**
 * Created by samuel.septiano on 06/04/2025.
 */
fun String.stringToColor(): Color? {
    return when (this) {
        "Blue" -> Color.Blue
        "Red" -> Color.Red
        "White" -> Color.White
        "Black" -> Color.Black
        "Gray" -> Color.Gray
        else -> null
    }
}