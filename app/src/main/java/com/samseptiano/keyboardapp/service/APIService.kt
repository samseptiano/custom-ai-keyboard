package com.samseptiano.keyboardapp.service

import com.samseptiano.keyboardapp.model.request.GenerateAIRequestModel
import com.samseptiano.keyboardapp.model.response.GenerateAIResponseModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by samuel.septiano on 06/04/2025.
 */
interface APIService {
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun postGenerateAI(
        @Query("key") apiKey: String,
        @Body body: GenerateAIRequestModel
    ): GenerateAIResponseModel
}