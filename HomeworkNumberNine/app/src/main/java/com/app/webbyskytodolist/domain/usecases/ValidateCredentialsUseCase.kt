package com.app.webbyskytodolist.domain.usecases

class ValidateCredentialsUseCase {
    fun isValidUsername(login: String): Boolean {
        val loginRegex = Regex("^[0-9A-Za-z]{6,16}$")
        return loginRegex.matches(login)
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8 || password.length > 24) return false
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val specialCharacters = setOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '?', '.', ',', '<', '>', '/', '\\', '|', '[', ']', '{', '}', ':', ';', '"', '\'')
        val hasSpecialChar = password.any { it in specialCharacters }
        return hasDigit && hasUpperCase && hasLowerCase && hasSpecialChar
    }

    fun passwordsMatch(password1: String, password2: String): Boolean {
        return password1 == password2
    }
}