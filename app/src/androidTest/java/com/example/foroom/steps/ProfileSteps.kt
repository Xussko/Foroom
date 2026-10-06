package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {
    private val profilePage = ProfilePage()
    private val passwordPage = ChangePasswordPage()
    private val languagePage = ChangeLanguagePage()

    fun openProfile() = profilePage.open()

    fun changePassword(password: String) {
        profilePage.openChangePassword()
        passwordPage.enterPassword(password)
        passwordPage.repeatPassword(password)
        passwordPage.confirm()
    }

    fun selectGeorgianAndVerify() {
        profilePage.openChangeLanguage()
        languagePage.selectGeorgian()
        profilePage.assertLanguageLabel("ენის შეცვლა")
    }

    fun selectEnglishAndVerify() {
        profilePage.openChangeLanguage()
        languagePage.selectEnglish()
        profilePage.assertLanguageLabel("Change Language")
    }
}
