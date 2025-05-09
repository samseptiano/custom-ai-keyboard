package com.samseptiano.keyboardapp.ui.screens.keyboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.samseptiano.keyboardapp.Presentation.GenerateAIViewModel
import com.samseptiano.keyboardapp.R
import com.samseptiano.keyboardapp.ui.theme.White


@Composable
fun KeyboardScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    keyColor: Color = Color.Blue,
    textColor: Color = Color.White,
    generateAIViewModel: GenerateAIViewModel,
    promptTextState: MutableState<String>
) {
    val isLoading by generateAIViewModel.isLoading

    generateAIViewModel.generateAIResultData.value.apply {
        candidates?.first()?.content?.parts?.first()
            ?.let {
                Log.d("selectedText after", it.text)
                promptTextState.value = it.text
            }
        GenerateAIViewModel()
    }

    val keyboardState = remember { mutableStateOf(KeyboardState.CAPS) }


    val keysArray = when (keyboardState.value) {
        KeyboardState.CAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", "AI", ",", " ", ".", "enter")
        )

        KeyboardState.NOCAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
            arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            arrayOf("shift", "z", "x", "c", "v", "b", "n", "m", "delete"),
            arrayOf("123", "emoji", "AI", ",", " ", ".", "enter")
        )

        KeyboardState.DOUBLECAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("SHIFT", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", "AI", ",", " ", ".", "enter")
        )

        KeyboardState.NUMBER -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
            arrayOf("=", "*", "\"", "'", ":", ";", "!", "?", "delete"),
            arrayOf("ABC", ",", "_", " ", "/", ".", "enter")
        )

        KeyboardState.EMOJI -> arrayOf(
            arrayOf("😀", "😁", "😂", "🤣", "😊", "😍", "😎", "😢", "😡", "😴"),
            arrayOf("👍", "🙏", "👏", "🙌", "💪", "👀", "🧠", "💀", "👻", "🎉"),
            arrayOf("🍕", "🍔", "🍟", "🌭", "🍣", "🍩", "☕", "🍺", "🚀", "❤️"),
            arrayOf("ABC", "🌍", "🎵", " ", "⌫", "⚙️", "✔️", "⏎")
        )

        else -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            arrayOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "delete"),
            arrayOf("123", "emoji", "AI", ",", " ", ".", "enter")
        )
    }
    Column(
        modifier = modifier
            .background(keyColor)
            .fillMaxWidth()
    ) {
        PromptResultWithCopy(promptTextState.value)
        Spacer(modifier = Modifier.height(16.dp))
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // ← fills only the keyboard's area, not full screen
                    .height(300.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .zIndex(1f)
                    .pointerInput(Unit) { }, // block touches if needed
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Generating with AI...", color = Color.White, fontSize = 14.sp)
                }
            }
        } else {
            keysArray.forEach { row ->
                FixedHeightBox(modifier = modifier.fillMaxWidth(), height = 60.dp) {
                    Row(modifier) {
                        row.forEach { key ->

                            val keyModifier = when (key) {
                                " " -> modifier.weight(3.54f)
                                "enter" -> modifier.weight(2f)
                                else -> modifier.weight(1f)
                            }

                            KeyboardKey(
                                vm = generateAIViewModel,
                                keyboardKey = key,
                                modifier = keyModifier,
                                keyboardState = keyboardState,
                                keyColor = keyColor,
                                textColor = textColor
                            )

                        }

                    }
                }
            }
        }
    }
}


@Composable
fun PromptResultWithCopy(promptTextState: String) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            TextField(
                value = promptTextState,
                onValueChange = {},
                readOnly = true,
                label = { Text("Prompt Result") },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 60.dp, max = if (isExpanded) 200.dp else 80.dp)
                    .verticalScroll(rememberScrollState())
                    .background(White, shape = RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    ),
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 14.sp
                ),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    disabledTextColor = Color.Black,
                    disabledLabelColor = Color.DarkGray,
                    disabledIndicatorColor = Color.Transparent,
                ),
                enabled = false
            )

            if (promptTextState.isNotBlank()) {
                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Copied Text", promptTextState)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy_clipboard_24),
                        contentDescription = "Copy"
                    )
                }
            }
        }

        // Expand/Collapse toggle
        if (promptTextState.length > 80) { // optional: only show toggle if long enough
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}


@Composable
fun FixedHeightBox(
    modifier: Modifier,
    height: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val h = height.roundToPx()
        layout(constraints.minWidth, h) {
            placeables.forEach { placeable ->
                placeable.place(
                    x = 0,
                    y = kotlin.math.min(0, h - placeable.height)
                )
            }
        }
    }
}


