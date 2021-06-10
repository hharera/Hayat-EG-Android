package com.whiteside.dwaa.ui.login.confirm

import android.content.Intent
import android.os.Bundle
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mindorks.example.coroutines.utils.Status
import com.whiteside.dwaa.R
import com.whiteside.dwaa.common.BaseActivity
import com.whiteside.dwaa.custom.PromptCodeDialog
import com.whiteside.dwaa.databinding.ActivityLoginConfirmBinding
import com.whiteside.dwaa.ui.home.HomeActivity
import com.whiteside.dwaa.common.ExtrasConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConfirmLoginActivity : BaseActivity() {

    private lateinit var waitDialog: PromptCodeDialog
    lateinit var confirmLoginViewMode: ConfirmLoginViewModel
    lateinit var bind: ActivityLoginConfirmBinding

    private lateinit var phoneNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmLoginViewMode = ViewModelProvider(this).get(ConfirmLoginViewModel::class.java)

        bind = ActivityLoginConfirmBinding.inflate(layoutInflater)
        setContentView(bind.root)

        phoneNumber = intent.extras!!.getString(ExtrasConstants.phoneNumber)!!
        observeCode()
        showWaitingDialog()
        sendVerificationCode(phoneNumber)

        confirmLoginViewMode.code.observe(this) {
            bind.code.text = it
            confirmLoginViewMode.checkCodeValidity()
        }

        confirmLoginViewMode.codeValidity.observe(this) {
            bind.next.isEnabled = it
        }

        bind.gridLayout.forEach {
            it.setOnClickListener {
                when (it.id) {
                    R.id.zero -> confirmLoginViewMode.codeChanged("0")
                    R.id.one -> confirmLoginViewMode.codeChanged("1")
                    R.id.two -> confirmLoginViewMode.codeChanged("2")
                    R.id.three -> confirmLoginViewMode.codeChanged("3")
                    R.id.four -> confirmLoginViewMode.codeChanged("4")
                    R.id.five -> confirmLoginViewMode.codeChanged("5")
                    R.id.six -> confirmLoginViewMode.codeChanged("6")
                    R.id.seven -> confirmLoginViewMode.codeChanged("7")
                    R.id.eight -> confirmLoginViewMode.codeChanged("8")
                    R.id.nine -> confirmLoginViewMode.codeChanged("9")
                    R.id.remove -> confirmLoginViewMode.removeChar()
                }
            }
        }

        bind.next.setOnClickListener {
            observeOperation()
            confirmLoginViewMode.login()
        }
    }

    private fun observeOperation() {
        confirmLoginViewMode.loginOperation.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    handleSuccess()
                    goToHomeActivity()
                }

                Status.ERROR -> {
                    handleFailure(it.error!!)
                }

                Status.LOADING -> {
                    handleLoading(R.string.loading)
                }
            }
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_right, 0)
    }

    private fun showWaitingDialog() {
        waitDialog = PromptCodeDialog()
        waitDialog.show(supportFragmentManager, "null")

        GlobalScope.launch(Dispatchers.Main) {
            for (i in 30 downTo 1) {
                waitDialog.updateView(i)
                delay(1000)
            }
            waitDialog.dismiss()
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(confirmLoginViewMode.createCallBack())
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun observeCode() {
        confirmLoginViewMode.verificationCode.observe(this) {
            if (it.status == Status.SUCCESS) {
                waitDialog.dismiss()
            } else if (it.status == Status.ERROR) {
                waitDialog.dismiss()
                handleFailure(it.error!!)
                showWaitingDialog()
                sendVerificationCode(phoneNumber)
            }
        }
    }
}