package com.harera.hayat.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.harera.hayat.ui.connection.NoInternetConnection
import com.harera.hayat.utils.Connectivity
import javax.inject.Inject

class ConnectivityReceiver : BroadcastReceiver() {

    @Inject
    lateinit var connectivity: Connectivity

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!connectivity.isConnected()) {
            context!!.startActivity(Intent(context, NoInternetConnection::class.java))
        }
    }
}