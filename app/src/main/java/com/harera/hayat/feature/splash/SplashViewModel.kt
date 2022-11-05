package com.harera.hayat.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harera.hayat.R
import com.harera.hayat.feature.login.LoginFormState
import com.harera.hayat.utils.Validity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

    private var _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    init {
        checkLoginState()
    }

    private fun checkLoginState() {

    }
}