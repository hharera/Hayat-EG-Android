package com.harera.dwaa.common.validatore

object FloatValidator {

    private const val AMOUNT_PATTERN = "^[0-9]{1,3}+(\\.[0-9])?\$"

    fun isAmountValid(amount: String): Boolean {
        return amount.matches(AMOUNT_PATTERN.toRegex())
    }
}