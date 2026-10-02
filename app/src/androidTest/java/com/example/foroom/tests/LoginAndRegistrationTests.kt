package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)
    private val cleanSessionRule = object : ExternalResource() {
        override fun before() = clearSession()

        override fun after() = clearSession()

        private fun clearSession() {
            runBlocking {
                GlobalContext.get().get<ForoomUserDataStore>().clearUserData()
            }
        }
    }

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(cleanSessionRule).around(activityRule)

    @Before
    fun verifyLoginScreen() {
        loginSteps.verifyLoginScreen()
    }

    @Test
    fun validUsernameAndInvalidPasswordShowsPasswordError() {
        loginSteps.logIn("student", "WrongPassword123!")
        loginSteps.verifyPasswordError()
    }

    @Test
    fun invalidUsernameAndInvalidPasswordShowsBothErrors() {
        loginSteps.logIn(uniqueUsername(), "WrongPassword123!")
        loginSteps.verifyUsernameError()
        loginSteps.verifyPasswordError()
    }

    @Test
    fun successfulRegistrationDisplaysHomeScreen() {
        loginSteps.openRegistration()
        registrationSteps.verifyRegistrationScreen()
        registrationSteps.register(uniqueUsername(), "ForoomTest123!")
        registrationSteps.verifySuccessfulRegistration()
    }

    private fun uniqueUsername(): String {
        val suffix = UUID.randomUUID().toString().replace("-", "")
        return "espresso_$suffix"
    }
}
