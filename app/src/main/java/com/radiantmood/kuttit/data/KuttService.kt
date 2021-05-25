package com.radiantmood.kuttit.data

import com.radiantmood.kuttit.data.KuttLinkResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface KuttService {
    @GET("links")
    suspend fun getLinks(@Header("X-API-KEY") apiKey: String): KuttLinkResponse
}