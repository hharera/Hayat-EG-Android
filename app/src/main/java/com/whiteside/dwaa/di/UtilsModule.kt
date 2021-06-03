package com.whiteside.dwaa.di

import android.app.Application
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    companion object {
        @Provides
        @Singleton
        fun provideConnectivity(application: Application) =
            application.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
