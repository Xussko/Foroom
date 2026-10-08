package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.LoginPage
import com.example.foroom.pages.ProfilePage

class LoginSteps {
    private val loginPage = LoginPage()
    private val chatsPage = ChatsPage()
    private val profilePage = ProfilePage()

    fun establishLoginScreen() {
        chatsPage.waitForEntryScreen()
        if (!loginPage.isDisplayed()) {
            if (!chatsPage.isHomeDisplayed()) chatsPage.closeChat()
            profilePage.open()
            profilePage.signOut()
        }
        loginPage.assertDisplayed()
    }

    fun logIn(username: String, password: String) {
        loginPage.assertDisplayed()
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.submit()
        chatsPage.assertHomeDisplayed()
    }

    fun assertLoginDisplayed() = loginPage.assertDisplayed()
}
