package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import com.example.design_system.R as DesignR

class ChatsPage {
    fun waitForEntryScreen() {
        Ui.waitFor(anyOf(withId(R.id.logInButton), withId(R.id.navBar), withId(R.id.closeButton)))
    }

    fun isHomeDisplayed() = Ui.isVisible(withId(R.id.navBar))

    fun assertHomeDisplayed() {
        Ui.waitFor(withId(R.id.navBar))
        Ui.waitFor(withId(R.id.homeNavigationChats))
    }

    fun assertCreatedChatDisplayed(name: String) {
        Ui.waitFor(withId(R.id.messagesRecyclerView))
        Ui.waitFor(allOf(
            withId(DesignR.id.chatNameTextView),
            isDescendantOfA(withId(R.id.chatHeaderView)),
            withText(name)
        ))
    }

    fun closeChat() {
        Ui.tap(withId(R.id.closeButton))
        Ui.waitFor(withId(R.id.chatsRecyclerView))
    }

    fun search(name: String) = Ui.enterText(R.id.searchChatInput, name)

    fun assertChatListed(name: String) {
        Ui.waitFor(allOf(
            withId(DesignR.id.chatTitleTextView),
            isDescendantOfA(withId(R.id.chatsRecyclerView)),
            withText(name)
        ))
    }
}
