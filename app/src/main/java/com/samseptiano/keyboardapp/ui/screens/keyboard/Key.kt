package com.samseptiano.keyboardapp.ui.screens.keyboard

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputConnection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samseptiano.keyboardapp.Presentation.GenerateAIViewModel
import com.samseptiano.keyboardapp.R
import com.samseptiano.keyboardapp.service.KeyboardService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeyboardKey(
    vm: GenerateAIViewModel,
    keyboardKey: String,
    modifier: Modifier,
    keyboardState: MutableState<KeyboardState>,
    keyColor: Color = Color.Blue,
    textColor: Color = Color.Black
) {

    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val ctx = LocalContext.current
    val iconMap = hashMapOf(
        "emoji" to R.drawable.outline_emoji_emotions_24,
        "shift" to R.drawable.shift_outlined,
        "Shift" to R.drawable.shift_filled,
        "SHIFT" to R.drawable.shfit_double_caps,
        "delete" to R.drawable.baseline_backspace_24,
        "enter" to R.drawable.baseline_send,
        " " to R.drawable.spacebar_filled,
        "123" to R.drawable.baseline_123,
        "ABC" to R.drawable.baseline_abc,
        "AI" to R.drawable.outline_ai_icon_24,

        )
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Card(
            elevation = 5.dp,
            modifier = modifier,
            backgroundColor = keyColor
        ) {
            if (iconMap.containsKey(keyboardKey)) {
                iconMap[keyboardKey]?.let {
                    KeyboardIcon(
                        vm = vm,
                        ctx = ctx,
                        modifier = modifier,
                        keyboardKey = keyboardKey,
                        drawable = it,
                        keyboardState = keyboardState,
                        textColor = textColor
                    )
                }
            } else {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            Log.d("typing keyboard", keyboardKey)
                            if (keyboardState.value == KeyboardState.CAPS) {
                                keyboardState.value = KeyboardState.NOCAPS
                            }
                            (ctx as KeyboardService).currentInputConnection.commitText(
                                keyboardKey,
                                keyboardKey
                                    .length
                            )
                        }
                        .background(keyColor)
                        .padding(
                            top = 14.dp,
                            bottom = 14.dp
                        ),
                    text = keyboardKey,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (pressed.value) {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(keyColor)
                            .padding(
                                top = 16.dp,
                                bottom = 48.dp
                            ),
                        text = keyboardKey,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardIcon(
    vm: GenerateAIViewModel,
    ctx: Context,
    modifier: Modifier = Modifier,
    keyboardKey: String,
    drawable: Int,
    keyboardState: MutableState<KeyboardState>,
    textColor: Color = Color.Black
) {
    val currentInputConnection = (ctx as KeyboardService).currentInputConnection

    Icon(
        modifier = modifier
            .fillMaxWidth()
            .pointerInteropFilter {

                when (it.action) {
                    MotionEvent.ACTION_DOWN -> whenKeyClick(
                        vm = vm,
                        motionEvent = it,
                        keyboardKey = keyboardKey,
                        keyboardState = keyboardState,
                        currentInputConnection = currentInputConnection
                    )
                }
                true
            }
            .padding(2.dp)
            .padding(
                top = 14.dp,
                bottom = 14.dp
            ),
        painter = painterResource(id = drawable),
        contentDescription = stringResource(R.string.key_icon),
        tint = textColor
    )
}


fun whenKeyClick(
    vm: GenerateAIViewModel,
    motionEvent: MotionEvent,
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    currentInputConnection: InputConnection
) {
    when (keyboardKey) {
        " " -> {
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_SPACE
                )
            )
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_LANGUAGE_SWITCH
                )
            )
        }

        "Shift" -> {
            keyboardState.value = KeyboardState.DOUBLECAPS
        }

        "SHIFT" -> {
            keyboardState.value = KeyboardState.NOCAPS
        }

        "shift" -> {
            keyboardState.value = KeyboardState.CAPS
        }

        "delete" -> {
            CoroutineScope(Dispatchers.IO).launch {
                while (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    delay(100)
                    currentInputConnection.sendKeyEvent(
                        KeyEvent(
                            KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_DEL
                        )
                    )
                }
            }
        }

        "enter" -> {
            currentInputConnection.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_ENTER
                )
            )
        }

        "AI" -> {
            val selectedText = currentInputConnection.getSelectedText(0)?.toString()
            Log.d("selectedText", selectedText.toString())
            if (selectedText.toString().trim().isNotEmpty()) {
                vm.generateAI(selectedText.toString())
            }
        }

        "123" -> keyboardState.value = KeyboardState.NUMBER
        "ABC" -> keyboardState.value = KeyboardState.STRING
        "emoji" -> keyboardState.value = KeyboardState.EMOJI
    }
}