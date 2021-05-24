package com.radiantmood.kuttit

import com.radiantmood.kuttit.data.KuttResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface KuttService {
    @GET("links")
    suspend fun getLinks(@Header("X-API-KEY") apiKey: String): KuttResponse
}