package com.harera.hayat.data.service.di

import com.harera.hayat.utils.Request
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KtorModule {

    companion object {
        @Provides
        @Singleton
        fun provideKtorClient(): HttpClient {
            return HttpClient(Android) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                    )
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = Request.TIME_OUT
                }
            }
        }
    }
}