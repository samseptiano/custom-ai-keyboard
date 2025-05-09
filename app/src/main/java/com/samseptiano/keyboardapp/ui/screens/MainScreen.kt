package com.samseptiano.keyboardapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samseptiano.keyboardapp.R

/**
 * Created by samuel.septiano on 06/04/2025.
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onInstallClicked: () -> Unit,
    onButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val openDialog = remember { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to " + stringResource(R.string.app_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.app_tutorial),
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
        }

        Button(
            shape = RoundedCornerShape(50),
            onClick = {
                openDialog.value = true
                onButtonClicked.invoke()
            }
        ) {
            Text(text = "Change system keyboard")
        }

        if (openDialog.value) {
            CustomAlertDialog(
                openDialog = openDialog,
                onInstallClicked = onInstallClicked,
                title = stringResource(R.string.change_system_keyboard),
                text = stringResource(R.string.change_system_keyboard_description),
                confirmText = stringResource(R.string.change_keyboard),
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    openDialog: MutableState<Boolean>,
    onInstallClicked: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmText: String,
) {
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = title?.let { { Text(text = title) } },
        text = text?.let { { Text(text = text) } },
        confirmButton = {
            Button(onClick = {
                openDialog.value = false
                onInstallClicked.invoke()
            }) {
                Text(text = confirmText)
            }
        },
    )
}