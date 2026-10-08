package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui

class CreateChatPage {
    fun open() {
        Ui.tap(withId(R.id.homeNavigationCreateChat))
        Ui.waitFor(withId(R.id.chatNameInput))
    }

    fun enterName(name: String) = Ui.enterText(R.id.chatNameInput, name)

    fun selectImage() {
        val firstRow = Ui.childAt(withId(R.id.chatImageChooser), 0)
        Ui.tap(Ui.childAt(firstRow, 2))
    }

    fun create() = Ui.tap(withId(R.id.createChatButton))
}
