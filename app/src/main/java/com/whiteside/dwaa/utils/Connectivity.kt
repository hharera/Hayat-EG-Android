package com.whiteside.dwaa.utils

import android.app.Application
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class Connectivity(private val application: Application) {
    val connectivity: ConnectivityManager by lazy {
        application.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE)
                as ConnectivityManager
    }

    fun isConnected(): Boolean = connectivity.activeNetwork != null
}