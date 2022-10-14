package com.harera.dwaa.common.validatore

import android.util.Patterns.EMAIL_ADDRESS

object StringValidator {

    fun isEmailValid(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }
}