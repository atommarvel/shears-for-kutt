package com.radiantmood.kuttit.network

import com.radiantmood.kuttit.repo.KuttApiKeySource
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds the user supplied api key to all okhttp requests to kutt
 */
class KuttApiKeyInterceptor(private val kuttApiKeySource: KuttApiKeySource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .addHeader("X-API-KEY", kuttApiKeySource.apiKey)
            .let { chain.proceed(it.build()) }
    }
}