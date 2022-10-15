package com.harera.hayat.utils

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

        fun isValidPhoneNumber(username: String): Boolean {
            return username.matches(Regex("^[0-9]{10}\$"))
        }

        fun isValidUsername(username: String): Boolean {
            return username.matches(Regex("^\\.{5,50}\$"))
        }

        fun isValidEmail(username: String): Boolean {
            return username.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())
        }

        fun isValidPassword(password: String): Boolean {
            return password.matches(Regex("^\\.{8,50}\$"))
        }
    }
}