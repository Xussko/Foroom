package com.example.foroom.steps

import com.example.foroom.pages.RegistrationPage

class RegistrationSteps(private val page: RegistrationPage = RegistrationPage()) {
    fun verifyRegistrationScreen() = page.assertIsDisplayed()

    fun register(username: String, password: String) {
        page.enterUsername(username)
        page.enterPassword(password)
        page.repeatPassword(password)
        page.selectAvatar()
        page.tapSignUp()
    }

    fun verifySuccessfulRegistration() = page.assertHomeIsDisplayed()
}
