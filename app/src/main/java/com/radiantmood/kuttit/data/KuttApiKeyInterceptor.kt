package com.radiantmood.kuttit.data

import com.radiantmood.kuttit.repo.SettingsRepo
import okhttp3.Interceptor
import okhttp3.Response

class KuttApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .addHeader("X-API-KEY", SettingsRepo.apiKey.orEmpty())
            .let { chain.proceed(it.build()) }
    }
}