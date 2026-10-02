package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserListView
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

class RegistrationPage {
    private val avatarList = withId(R.id.listView)
    private val signUpButton = withId(R.id.signUpButton)
    private val secondAvatar = childAtPosition(childAtPosition(avatarList, 0), 2)

    fun assertIsDisplayed() {
        assertDisplayed(withId(R.id.repeatPasswordInput))
        assertDisplayed(withId(R.id.userNameInput))
        assertDisplayed(withId(R.id.passwordInput))
        assertDisplayed(avatarList)
        assertDisplayed(signUpButton)
    }

    fun enterUsername(username: String) = enterInput(R.id.userNameInput, username)

    fun enterPassword(password: String) = enterInput(R.id.passwordInput, password)

    fun repeatPassword(password: String) = enterInput(R.id.repeatPasswordInput, password)

    fun selectAvatar() {
        val avatarsReady = object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("avatar chooser with loaded avatars")
            }

            override fun matchesSafely(view: View) =
                view is ImageChooserListView && view.isChoosingEnabled && view.images.size >= 2
        }
        waitForView(allOf(avatarList, isDisplayed(), avatarsReady))
        onView(secondAvatar).perform(click())
        onView(avatarList).check { view, error ->
            if (error != null) throw error
            check((view as ImageChooserListView).selectedIndex == 1) {
                "The second avatar was not selected"
            }
        }
    }

    fun tapSignUp() {
        onView(signUpButton).perform(click())
    }

    fun assertHomeIsDisplayed() {
        assertDisplayed(withId(R.id.homeContainer))
        assertDisplayed(withId(R.id.navBar))
    }
}
