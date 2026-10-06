package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val arguments = InstrumentationRegistry.getArguments()
    private val username = arguments.getString("testUsername") ?: "xussko_android3"
    private val password = arguments.getString("testPassword") ?: "Android3Test123!"
    private val fullName = arguments.getString("studentFullName") ?: "Nikoloz Khuskivadze"
    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    @Before
    fun setUp() {
        loginSteps.establishLoginScreen()
        loginSteps.logIn(username, password)
    }

    @Test
    fun changePasswordAndVerifySuccessfulLogin() {
        val newPassword = "Updated3!${UUID.randomUUID().toString().take(8)}"
        profileSteps.openProfile()
        profileSteps.changePassword(newPassword)
        try {
            loginSteps.assertLoginDisplayed()
            loginSteps.logIn(username, newPassword)
        } finally {
            loginSteps.establishLoginScreen()
            loginSteps.logIn(username, newPassword)
            profileSteps.openProfile()
            profileSteps.changePassword(password)
            loginSteps.assertLoginDisplayed()
            loginSteps.logIn(username, password)
        }
    }

    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {
        profileSteps.openProfile()
        profileSteps.selectGeorgianAndVerify()
        profileSteps.selectEnglishAndVerify()
        profileSteps.selectGeorgianAndVerify()
    }

    @Test
    fun createChatAndFindItInChatList() {
        val chatName = "$fullName ${UUID.randomUUID().toString().take(8)}"
        chatSteps.createChatAndVerify(chatName)
        chatSteps.findCreatedChatAndVerify(chatName)
    }
}
