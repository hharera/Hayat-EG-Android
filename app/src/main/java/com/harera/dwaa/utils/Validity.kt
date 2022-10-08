package com.harera.dwaa.utils

class Validity {

    companion object {

        fun checkPhoneNumber(string: String) =
            string.matches(Regex("^[0-9]{10}\$"))

        fun checkCode(string: String) =
            string.matches(Regex("^[0-9]{6}\$"))

        fun checkName(string: String) =
            string.matches(Regex("^[a-z]{5,15}\$"))

        fun checkDesc(string: String) =
            string.matches(Regex("^[a-z]{10,200}\$"))

        fun checkPassword(string: String) =
            string.matches(Regex("^[a-z]{8,25}\$"))
    }
}