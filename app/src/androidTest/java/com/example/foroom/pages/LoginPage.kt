package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matchers.allOf

class LoginPage {
    private val logInButton = withId(R.id.logInButton)
    private val signUpButton = withId(R.id.signUpButton)

    fun assertIsDisplayed() {
        assertDisplayed(logInButton)
        assertDisplayed(withId(R.id.userNameInput))
        assertDisplayed(withId(R.id.passwordInput))
        assertDisplayed(signUpButton)
    }

    fun enterUsername(username: String) = enterInput(R.id.userNameInput, username)

    fun enterPassword(password: String) = enterInput(R.id.passwordInput, password)

    fun tapLogIn() {
        onView(logInButton).perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton).perform(click())
    }

    fun assertUsernameError(message: String) = assertInputError(R.id.userNameInput, message)

    fun assertPasswordError(message: String) = assertInputError(R.id.passwordInput, message)

    private fun assertInputError(parentId: Int, message: String) {
        val description = allOf(
            withId(DesignR.id.descriptionTextView),
            isDescendantOfA(withId(parentId))
        )
        waitForView(allOf(description, isDisplayed(), withText(message)))
        onView(description).check(matches(allOf(isDisplayed(), withText(message))))
    }
}
