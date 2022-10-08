package com.harera.dwaa.ui.connection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harera.dwaa.R
import com.harera.dwaa.common.ConnectionLiveData
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