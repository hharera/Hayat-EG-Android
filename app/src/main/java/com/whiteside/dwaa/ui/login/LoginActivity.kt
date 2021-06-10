package com.whiteside.dwaa.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.whiteside.dwaa.R
import com.whiteside.dwaa.databinding.ActivityLoginBinding
import com.whiteside.dwaa.ui.login.confirm.ConfirmLoginActivity
import com.whiteside.dwaa.common.ExtrasConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel
    lateinit var bind: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.phoneNumber.observe(this) {
            bind.phoneNumber.text = it
            loginViewModel.checkPhoneNumberValidity()
        }

        loginViewModel.phoneNumberValidity.observe(this) {
            bind.next.isEnabled = it
        }

        loginViewModel.policyAccepted.observe(this) {
            bind.next.isEnabled = bind.next.isEnabled && it
            if (it) {
                bind.acceptPolicy.setImageResource(R.drawable.verified)
            } else
                bind.acceptPolicy.setImageResource(R.drawable.not_verified)
        }

        bind.gridLayout.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> loginViewModel.changePhoneNumber("0")
                    R.id.one -> loginViewModel.changePhoneNumber("1")
                    R.id.two -> loginViewModel.changePhoneNumber("2")
                    R.id.three -> loginViewModel.changePhoneNumber("3")
                    R.id.four -> loginViewModel.changePhoneNumber("4")
                    R.id.five -> loginViewModel.changePhoneNumber("5")
                    R.id.six -> loginViewModel.changePhoneNumber("6")
                    R.id.seven -> loginViewModel.changePhoneNumber("7")
                    R.id.eight -> loginViewModel.changePhoneNumber("8")
                    R.id.nine -> loginViewModel.changePhoneNumber("9")
                    R.id.remove -> loginViewModel.removeChar()
                }
            }
        }

        bind.next.setOnClickListener {
            val phoneNumber = bind.phoneNumber.text.toString()
            val intent = Intent(this, ConfirmLoginActivity::class.java).apply {
                putExtra(ExtrasConstants.phoneNumber, "+20${phoneNumber}")
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        bind.acceptPolicy.setOnClickListener {
            loginViewModel.acceptPolicy()
        }
    }
}