package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui

class RegistrationPage {
    fun open() {
        Ui.tap(withId(R.id.signUpButton))
        Ui.waitFor(withId(R.id.repeatPasswordInput))
    }

    fun enterUsername(username: String) = Ui.enterText(R.id.userNameInput, username)

    fun enterPassword(password: String) {
        Ui.enterText(R.id.passwordInput, password)
        Ui.enterText(R.id.repeatPasswordInput, password)
    }

    fun selectAvatar() {
        val firstRow = Ui.childAt(withId(R.id.listView), 0)
        Ui.tap(Ui.childAt(firstRow, 2))
    }

    fun submit() = Ui.tap(withId(R.id.signUpButton))
}
