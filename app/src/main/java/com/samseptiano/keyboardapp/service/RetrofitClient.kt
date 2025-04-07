package com.samseptiano.keyboardapp.service

import com.samseptiano.keyboardapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by samuel.septiano on 06/04/2025.
 */
object RetrofitClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    fun create(): APIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIService::class.java)
    }
}
