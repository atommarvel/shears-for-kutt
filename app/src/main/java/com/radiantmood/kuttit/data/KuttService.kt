package com.radiantmood.kuttit.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface KuttService {
    @GET("links")
    suspend fun getLinks(@Query("skip") skip: Int): KuttLinkResponse

    @POST("links")
    suspend fun postLink(@Body body: NewKuttLinkBody): KuttLinkCreation

    @DELETE("links/{id}")
    suspend fun deleteLink(@Path("id") id: String)
}