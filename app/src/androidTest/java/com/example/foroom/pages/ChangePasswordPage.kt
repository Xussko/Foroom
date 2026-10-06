package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui
import com.example.design_system.R as DesignR

class ChangePasswordPage {
    fun enterPassword(password: String) = Ui.enterText(R.id.passwordInput, password)

    fun repeatPassword(password: String) = Ui.enterText(R.id.repeatPasswordInput, password)

    fun confirm() = Ui.tap(withId(DesignR.id.actionButton))
}
