package com.radiantmood.kuttit.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radiantmood.kuttit.KuttService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object RetrofitBuilder {
    private const val BASE_URL = "https://kutt.it/api/v2/"

    @OptIn(ExperimentalSerializationApi::class)
    private fun getRetrofit(): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(contentType)
            )
            .build()
    }

    val kuttService: KuttService = getRetrofit().create(KuttService::class.java)
}