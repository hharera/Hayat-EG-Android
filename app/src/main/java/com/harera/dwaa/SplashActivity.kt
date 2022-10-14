package com.harera.dwaa

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.api.LogDescriptor
import com.harera.dwaa.common.BaseActivity
import com.harera.dwaa.databinding.ActivitySplashBinding
import com.harera.dwaa.network.repository.AuthManager
import com.harera.dwaa.ui.home.HomeActivity
import com.harera.dwaa.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launchWhenCreated {
            delay(2000)
            goToHomeActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}