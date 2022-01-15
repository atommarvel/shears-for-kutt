package com.radiantmood.kuttit.data

import com.radiantmood.kuttit.repo.KuttApiKeySource
import okhttp3.Interceptor
import okhttp3.Response

class KuttApiKeyInterceptor(private val kuttApiKeySource: KuttApiKeySource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            // TODO: move .apiKey to local instance of SettingsRepo
            .addHeader("X-API-KEY", kuttApiKeySource.apiKey)
            .let { chain.proceed(it.build()) }
    }
}