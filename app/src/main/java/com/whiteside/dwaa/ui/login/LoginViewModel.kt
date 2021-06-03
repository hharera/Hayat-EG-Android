package com.whiteside.dwaa.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whiteside.dwaa.utils.Validity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private var _phoneNumber = MutableLiveData<String>("")
    val phoneNumber: LiveData<String> = _phoneNumber

    private var _phoneNumberValidity = MutableLiveData<Boolean>(false)
    val phoneNumberValidity: LiveData<Boolean> = _phoneNumberValidity

    private var _policyAccepted = MutableLiveData<Boolean>(false)
    val policyAccepted: LiveData<Boolean> = _policyAccepted

    fun changePhoneNumber(ch: String) {
        phoneNumber.value?.let {
            if (it.length < 10) {
                _phoneNumber.value = it.plus(ch)
            }
        }
    }

    fun checkPhoneNumberValidity() {
        _phoneNumberValidity.value = Validity.checkPhoneNumber(_phoneNumber.value!!)
    }

    fun acceptPolicy() {
        _policyAccepted.value = !_policyAccepted.value!!
    }

    fun removeChar() {
        if (_phoneNumber.value!!.length > 0) {
            _phoneNumber.value = _phoneNumber.value!!.dropLast(1)
        }
    }


}