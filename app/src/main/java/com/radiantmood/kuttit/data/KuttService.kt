package com.radiantmood.kuttit.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface KuttService {
    @GET("links")
    suspend fun getLinks(
        @Header("X-API-KEY") apiKey: String, // TODO: move key to an interceptor
        @Query("skip") skip: Int
    ): KuttLinkResponse

    @POST("links")
    suspend fun postLink(
        @Header("X-API-KEY") apiKey: String, // TODO: move key to an interceptor
        @Body body: NewKuttLinkBody
    )
}