package com.whiteside.dwaa.ui.login.confirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mindorks.example.coroutines.utils.Response
import com.whiteside.dwaa.network.repository.AuthManager
import com.whiteside.dwaa.utils.Validity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmLoginViewModel @Inject constructor(private val authManager: AuthManager) :
    ViewModel() {
    private var _code = MutableLiveData<String>("")
    val code: LiveData<String> = _code

    private var _codeValidity = MutableLiveData<Boolean>(false)
    val codeValidity: LiveData<Boolean> = _codeValidity

    private var _verificationCode = MutableLiveData<Response<String>>()
    val verificationCode: LiveData<Response<String>> = _verificationCode

    private var _loginOperation = MutableLiveData<Response<String>>()
    val loginOperation: LiveData<Response<String>> = _loginOperation

    fun codeChanged(ch: String) {
        code.value?.let {
            if (it.length < 6) {
                _code.value = it.plus(ch)
            }
        }
    }

    fun checkCodeValidity() {
        _codeValidity.value = Validity.checkCode(_code.value!!)
    }

    fun removeChar() {
        if (_code.value!!.length > 0) {
            _code.value = _code.value!!.dropLast(1)
        }
    }

    fun createCallBack() =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                _verificationCode.value = Response.error(e, null)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _verificationCode.value = Response.success(verificationId)


            }
        }

    fun login() {
        _loginOperation.value = Response.loading(null)
        GlobalScope.launch {
            val credential = authManager
                .createCredential(verificationCode.value!!.data!!, code.value!!)
            authManager.login(credential)
                .addOnSuccessListener {
                    _loginOperation.value = Response.success(it.toString())
                }
                .addOnFailureListener {
                    _loginOperation.value = Response.error(it, null)
                }
        }
    }
}