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