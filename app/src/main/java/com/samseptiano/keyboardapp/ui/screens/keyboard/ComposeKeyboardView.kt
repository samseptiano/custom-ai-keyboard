package com.samseptiano.keyboardapp.ui.screens.keyboard

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AbstractComposeView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samseptiano.keyboardapp.Presentation.GenerateAIViewModel
import com.samseptiano.keyboardapp.model.response.GenerateAIResponseModel
import com.samseptiano.keyboardapp.utils.stringToColor

class ComposeKeyboardView(context: Context) : AbstractComposeView(context) {
    /**
     * The Jetpack Compose UI content for this view.
     * Subclasses must implement this method to provide content. Initial composition will
     * occur when the view becomes attached to a window or when [createComposition] is called,
     * whichever comes first.
     */

    var _keyboardVisibility = mutableStateOf(true)

    @Composable
    override fun Content() {
        val isKeyboardVisible by remember { _keyboardVisibility }
        val promptTextState = remember { mutableStateOf("") }

        val generateAIViewModel: GenerateAIViewModel = viewModel()
        val pre = context.getSharedPreferences("keyboard_color", Context.MODE_PRIVATE)
        val background = pre.getString("background", "White")
        val key = pre.getString("key", "White")
        val text = pre.getString("text", "Black")
        val backgroundColor = background?.stringToColor()!!
        val keyColor = key?.stringToColor()!!
        val textColor = text?.stringToColor()!!

        // Now this will reactively run when `isKeyboardVisible` is false
        if (isKeyboardVisible) {
            LaunchedEffect(Unit) {
                generateAIViewModel.generateAIResultData.value = GenerateAIResponseModel()
                promptTextState.value = ""
            }
        }

        KeyboardScreen(
            backgroundColor = backgroundColor,
            keyColor = keyColor,
            textColor = textColor,
            generateAIViewModel = generateAIViewModel,
            promptTextState = promptTextState
        )
    }

    fun onCloseKeyboardView() {
        _keyboardVisibility.value = false

    }
}