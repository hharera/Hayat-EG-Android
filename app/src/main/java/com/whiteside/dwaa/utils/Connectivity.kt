package com.whiteside.dwaa.utils

import android.net.ConnectivityManager
import javax.inject.Inject

class Connectivity @Inject constructor(private val connectivity: ConnectivityManager) {

    fun isConnected() =
        connectivity.activeNetwork != null
}