package com.whiteside.dwaa.ui.connection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.ConnectionLiveData
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