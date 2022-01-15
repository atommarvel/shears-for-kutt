package com.radiantmood.kuttit.di

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.radiantmood.kuttit.data.KuttApiKeyInterceptor
import com.radiantmood.kuttit.data.KuttService
import com.radiantmood.kuttit.repo.KuttApiKeySource
import com.radiantmood.kuttit.repo.KuttUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KuttSingletonModule {

    @Singleton
    @Provides
    fun prefs(application: Application) =
        application.getSharedPreferences("kutt.prefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun json() = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun kuttApiKeyInterceptor(kuttApiKeySource: KuttApiKeySource) =
        KuttApiKeyInterceptor(kuttApiKeySource)

    @Singleton
    @Provides
    fun okHttpClient(apiKeyInterceptor: KuttApiKeyInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

    @Singleton
    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun retrofit(client: OkHttpClient, json: Json, kuttUrlProvider: KuttUrlProvider) =
        Retrofit.Builder()
            .client(client)
            // TODO: add interceptor or something to dynamically change the baseUrl?
            .baseUrl(kuttUrlProvider.apiBaseUrl)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()

    @Singleton
    @Provides
    fun kuttService(retrofit: Retrofit): KuttService = retrofit.create(KuttService::class.java)
}