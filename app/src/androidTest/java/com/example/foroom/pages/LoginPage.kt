package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui

class LoginPage {
    fun isDisplayed() = Ui.isVisible(withId(R.id.logInButton))

    fun assertDisplayed() {
        Ui.waitFor(withId(R.id.logInButton))
        Ui.waitFor(Ui.input(R.id.userNameInput))
    }

    fun enterUsername(username: String) = Ui.enterText(R.id.userNameInput, username)

    fun enterPassword(password: String) = Ui.enterText(R.id.passwordInput, password)

    fun submit() = Ui.tap(withId(R.id.logInButton))
}
