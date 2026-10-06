package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui

class ChangeLanguagePage {
    fun selectGeorgian() = Ui.tap(withId(R.id.languageButtonGeo))

    fun selectEnglish() = Ui.tap(withId(R.id.languageButtonEng))
}
