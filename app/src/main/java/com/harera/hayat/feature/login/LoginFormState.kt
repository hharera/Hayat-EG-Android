package com.harera.hayat.feature.login

data class LoginFormState(
    val usernameError : Int? = null,
    val passwordError : Int? = null,
    val isValid : Boolean = false,
)
