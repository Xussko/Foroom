package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

class LoginSteps(private val page: LoginPage = LoginPage()) {
    fun verifyLoginScreen() = page.assertIsDisplayed()

    fun logIn(username: String, password: String) {
        page.enterUsername(username)
        page.enterPassword(password)
        page.tapLogIn()
    }

    fun openRegistration() = page.tapSignUp()

    fun verifyPasswordError() = page.assertPasswordError("Incorrect password")

    fun verifyUsernameError() = page.assertUsernameError("Username does not exist")
}
