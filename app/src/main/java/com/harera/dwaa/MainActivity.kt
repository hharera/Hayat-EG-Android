package com.harera.dwaa

import android.content.Intent
import android.os.Bundle
import com.harera.dwaa.common.BaseActivity
import com.harera.dwaa.databinding.ActivityMainBinding
import com.harera.dwaa.network.repository.AuthManager
import com.harera.dwaa.ui.home.HomeActivity
import com.harera.dwaa.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var bind: ActivityMainBinding

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        //TODO remove comment after development
        goToHomeActivity()
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(3000)
//            check()
//        }
    }

    private fun checkLogin() {
        if (authManager.getCurrentUser() != null) {
            goToHomeActivity()
        } else {
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