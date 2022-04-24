package com.radiantmood.kuttit.network

import com.radiantmood.kuttit.data.server.GetLinksResponse
import com.radiantmood.kuttit.data.server.PatchLinkBody
import com.radiantmood.kuttit.data.server.PostLinkBody
import com.radiantmood.kuttit.data.server.PostLinkResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface KuttService {
    @GET("links")
    suspend fun getLinks(@Query("skip") skip: Int): GetLinksResponse

    @POST("links")
    suspend fun postLink(@Body body: PostLinkBody): PostLinkResponse

    @DELETE("links/{id}")
    suspend fun deleteLink(@Path("id") id: String)

    @Deprecated("Updating links leads to undesirable behaviors. Combine delete/post instead.")
    @PATCH("links/{id}")
    suspend fun patchLink(@Path("id") id: String, @Body body: PatchLinkBody)
}