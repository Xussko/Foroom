package com.example.foroom.helpers

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DesignR

object Ui {
    fun recyclerPosition(recyclerId: Int, position: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("item at position $position in recycler $recyclerId")
            }

            override fun matchesSafely(view: View): Boolean {
                var ancestor = view.parent
                while (ancestor is View) {
                    if (ancestor is RecyclerView && ancestor.id == recyclerId) {
                        return ancestor.findContainingViewHolder(view)?.bindingAdapterPosition == position
                    }
                    ancestor = ancestor.parent
                }
                return false
            }
        }

    fun appears(matcher: Matcher<View>, timeoutMillis: Long = 5_000): Boolean {
        return try {
            waitFor(matcher, timeoutMillis)
            true
        } catch (failure: AssertionError) {
            false
        }
    }

    fun isVisible(matcher: Matcher<View>): Boolean {
        return try {
            onView(matcher).check(matches(isDisplayed()))
            true
        } catch (failure: NoMatchingViewException) {
            false
        } catch (failure: AssertionError) {
            false
        }
    }

    fun waitFor(matcher: Matcher<View>, timeoutMillis: Long = 15_000): ViewInteraction {
        val deadline = SystemClock.uptimeMillis() + timeoutMillis
        var lastFailure: Throwable? = null
        while (SystemClock.uptimeMillis() < deadline) {
            try {
                return onView(matcher).check(matches(isDisplayed()))
            } catch (failure: NoMatchingViewException) {
                lastFailure = failure
            } catch (failure: AssertionError) {
                lastFailure = failure
            }
            SystemClock.sleep(50)
        }
        throw AssertionError("Timed out waiting for a displayed view: $matcher", lastFailure)
    }

    fun tap(matcher: Matcher<View>) {
        waitFor(allOf(matcher, isEnabled())).perform(click())
    }

    fun enterText(parentId: Int, text: String) {
        waitFor(input(parentId)).perform(replaceText(text), closeSoftKeyboard())
    }

    fun input(parentId: Int): Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(parentId))
    )

    fun childAt(parentMatcher: Matcher<View>, index: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("child at index $index of ").appendDescriptionOf(parentMatcher)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent as? ViewGroup ?: return false
                return parentMatcher.matches(parent) && parent.indexOfChild(view) == index
            }
        }
}
