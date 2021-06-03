package com.whiteside.dwaa

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.whiteside.dwaa.common.BaseActivity
import com.whiteside.dwaa.databinding.ActivityMainBinding
import com.whiteside.dwaa.network.repository.AuthManager
import com.whiteside.dwaa.ui.home.HomeActivity
import com.whiteside.dwaa.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var bind: ActivityMainBinding

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            for (i in 1..4) {
                delay(600)
                bind.pill.rotation += 90
            }
            check()
        }
    }

    private fun check() {
        if (authManager.getCurrentUser() != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}