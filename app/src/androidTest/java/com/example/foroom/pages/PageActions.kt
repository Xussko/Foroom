package com.example.foroom.pages

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.TreeIterables
import com.example.design_system.R as DesignR
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.TimeoutException

internal fun waitForView(matcher: Matcher<View>, timeoutMillis: Long = 10_000) {
    onView(isRoot()).perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription() = "wait up to $timeoutMillis ms for $matcher"

        override fun perform(uiController: UiController, view: View) {
            val deadline = SystemClock.uptimeMillis() + timeoutMillis
            do {
                if (TreeIterables.breadthFirstViewTraversal(view).any(matcher::matches)) return
                uiController.loopMainThreadForAtLeast(50)
            } while (SystemClock.uptimeMillis() < deadline)

            throw PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(matcher.toString())
                .withCause(TimeoutException("View condition was not met: $matcher"))
                .build()
        }
    })
}

internal fun assertDisplayed(matcher: Matcher<View>) {
    waitForView(allOf(matcher, isDisplayed()))
    onView(matcher).check(matches(isDisplayed()))
}

internal fun enterInput(parentId: Int, text: String) {
    val input = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(parentId))
    )
    assertDisplayed(input)
    onView(input).perform(replaceText(text), closeSoftKeyboard())
}

internal fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> =
    object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("child at position $position of ").appendDescriptionOf(parentMatcher)
        }

        override fun matchesSafely(view: View): Boolean {
            val parent = view.parent as? ViewGroup ?: return false
            return parentMatcher.matches(parent) && parent.indexOfChild(view) == position
        }
    }
