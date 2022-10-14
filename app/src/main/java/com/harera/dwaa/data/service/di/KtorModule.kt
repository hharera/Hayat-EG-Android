package com.harera.dwaa.data.service.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.harera.dwaa.utils.Request
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
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