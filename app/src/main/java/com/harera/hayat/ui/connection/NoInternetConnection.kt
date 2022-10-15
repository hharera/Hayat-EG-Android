package com.harera.hayat.ui.connection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.harera.hayat.R
import com.harera.hayat.common.ConnectionLiveData
import kotlin.system.exitProcess

class NoInternetConnection : AppCompatActivity() {

    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_connection)

        connectionLiveData = ConnectionLiveData(this)
        setupObserver()
    }

    private fun setupObserver() {
        connectionLiveData.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        exitProcess(1)
    }
}