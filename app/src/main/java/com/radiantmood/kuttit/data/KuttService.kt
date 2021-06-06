package com.radiantmood.kuttit.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KuttService {
    @GET("links")
    suspend fun getLinks(
        @Header("X-API-KEY") apiKey: String,
        @Query("skip") skip: Int
    ): KuttLinkResponse
}