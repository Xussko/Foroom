package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class ProfilePage {
    fun open() {
        Ui.tap(withId(R.id.homeNavigationProfile))
        Ui.waitFor(withId(R.id.changePasswordItem))
    }

    fun openChangePassword() = Ui.tap(withId(R.id.changePasswordItem))

    fun openChangeLanguage() = Ui.tap(withId(R.id.changeLanguageItem))

    fun signOut() = Ui.tap(withId(R.id.signOutItem))

    fun assertLanguageLabel(label: String) {
        Ui.waitFor(allOf(
            withId(DesignR.id.listItemTextView),
            isDescendantOfA(withId(R.id.changeLanguageItem)),
            withText(label)
        ))
    }
}
