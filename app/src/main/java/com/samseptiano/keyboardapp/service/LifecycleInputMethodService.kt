package com.samseptiano.keyboardapp.service

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import com.samseptiano.keyboardapp.ui.screens.keyboard.ComposeKeyboardView

abstract class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {
    private val composeKeyboardView by lazy {
        ComposeKeyboardView(this)
    }

    val dispatcher = ServiceLifecycleDispatcher(this)


    override fun onCreateInputView(): View {
        return composeKeyboardView
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        Log.d("IME", "onFinishInputView: Keyboard is being closed")
        composeKeyboardView.onCloseKeyboardView()
    }

    override fun onFinishInput() {
        super.onFinishInput()
        Log.d("IME", "onFinishInput: Input finished, keyboard likely closing")
    }


    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBindInput() {
        super.onBindInput()
        dispatcher.onServicePreSuperOnBind()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

}