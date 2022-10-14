package com.harera.dwaa.feature.postingdonation

data class DonationFormState(
    val nameError : Int? = null,
    val expireDateError: Int? = null,
    val priceError : Int? = null,
    val typeError : Int? = null,
    val imageError : Int? = null,
    val locationError : Int? = null,
    val isValid : Boolean = false,
)
