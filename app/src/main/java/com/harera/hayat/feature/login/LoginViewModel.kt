package com.harera.hayat.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harera.hayat.R
import com.harera.hayat.utils.Validity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private var _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    fun setUsername(username: String) {
        _username.value = username
        checkFormValidity()
    }

    fun setPassword(password: String) {
        _password.value = password
        checkFormValidity()
    }

    private fun checkFormValidity() {
        val username = username.value
        val password = password.value
        if (username.isNullOrBlank()) {
            _loginFormState.value = LoginFormState(usernameError = R.string.empty_username)
        } else if (
            Validity.isValidEmail(username).not() and
            Validity.isValidUsername(username).not() and
            Validity.isValidPhoneNumber(username).not()
        ) {
            _loginFormState.value = LoginFormState(usernameError = R.string.error_invalid_username)
        } else if (password.isNullOrBlank()) {
            _loginFormState.value = LoginFormState(passwordError = R.string.error_empty_password)
        } else if (Validity.isValidPassword(password).not()) {
            _loginFormState.value = LoginFormState(passwordError = R.string.error_invalid_password)
        } else {
            _loginFormState.value = LoginFormState(isValid = true)
        }
    }

    fun login() {
    }
}