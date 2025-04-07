package com.samseptiano.keyboardapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.samseptiano.keyboardapp.ui.screens.MainScreen
import com.samseptiano.keyboardapp.ui.theme.KeyboardAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ctx = LocalContext.current
            val manager =
                ctx.applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            KeyboardAppTheme {
                MainScreen(
                    onButtonClicked = {
                        if(isThisKeyboardSetAsDefaultIME(ctx, manager)) {
                            ctx.startActivity(
                                Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
                            )
                        }
                    },
                    onInstallClicked = {
                        manager.showInputMethodPicker()
                    }
                )
            }
        }
    }

    private fun isThisKeyboardSetAsDefaultIME(context: Context, manager: InputMethodManager): Boolean {
        var bool = true
        val list = manager.enabledInputMethodList
        list.forEach {
            if(it.packageName == context.packageName)
                bool = false
        }
        return bool
    }
}