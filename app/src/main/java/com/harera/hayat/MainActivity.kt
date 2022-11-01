package com.harera.hayat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.harera.hayat.common.BaseActivity
import com.harera.hayat.databinding.ActivitySplashBinding
import com.harera.hayat.ui.home.HomeActivity
import com.harera.hayat.feature.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.apply {
                setKeepOnScreenCondition {
                    true
                }
                setOnExitAnimationListener { splashScreenViewProvider ->
                    splashScreenViewProvider.view
                        .animate()
                        .alpha(0f)
                        .setDuration(1000)
                        .withEndAction {
                            splashScreenViewProvider.remove()
                        }
                }
            }
        } else {
            setContentView(R.layout.activity_splash)
            setupAnimation()
        }
    }

    private fun setupAnimation() {
        lifecycleScope.launchWhenCreated {
            delay(2000)
            goToLoginActivity()
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