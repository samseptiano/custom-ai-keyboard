package com.samseptiano.keyboardapp.Presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samseptiano.keyboardapp.BuildConfig
import com.samseptiano.keyboardapp.model.request.GenerateAIRequestModel
import com.samseptiano.keyboardapp.model.request.Parts
import com.samseptiano.keyboardapp.model.request.Text
import com.samseptiano.keyboardapp.model.response.GenerateAIResponseModel
import com.samseptiano.keyboardapp.service.RetrofitClient
import kotlinx.coroutines.launch

/**
 * Created by samuel.septiano on 06/04/2025.
 */
class GenerateAIViewModel : ViewModel() {

    var isLoading = mutableStateOf(false)

    val generateAIResultData = mutableStateOf(GenerateAIResponseModel())

    init {
        generateAIResultData.value = GenerateAIResponseModel()
    }

    fun generateAI(prompt: String) {
        viewModelScope.launch {
            isLoading.value = true

            try {
                val request = GenerateAIRequestModel();

                val parts = Parts()
                parts.parts = arrayListOf()
                (parts.parts as ArrayList<Text>).add(Text(prompt))
                request.contents = arrayListOf()
                (request.contents as ArrayList<Parts>).add(parts)

                val response = RetrofitClient.create().postGenerateAI(BuildConfig.API_KEY, request)
                generateAIResultData.value = response
            } catch (e: Exception) {
                generateAIResultData.value = GenerateAIResponseModel()
                isLoading.value = false

            } finally {
                isLoading.value = false
            }
        }
    }
}