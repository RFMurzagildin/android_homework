package com.app.homework_number_six.utils

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidNickname(nickname: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]{3,20}$")
        return regex.matches(nickname)
    }

    fun isValidEmailOrNickname(input: String): Boolean {
        return isValidEmail(input) || isValidNickname(input)
    }

    fun isValidName(name: String): Boolean {
        val regex = Regex("^[a-zA-Zа-яА-ЯёЁ]{2,50}$")
        return regex.matches(name)
    }

    fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d).{6,}\$")
        return regex.matches(password)
    }
}