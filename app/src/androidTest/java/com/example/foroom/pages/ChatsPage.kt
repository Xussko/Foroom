package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.helpers.Ui
import com.example.design_system.components.chat.ForoomChatCardView
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import com.example.design_system.R as DesignR

class ChatsPage {
    private fun chatTitle(name: String) = allOf(
        withId(DesignR.id.chatTitleTextView),
        isDescendantOfA(withId(R.id.chatsRecyclerView)),
        withText(name),
        Ui.recyclerPosition(R.id.chatsRecyclerView, 0)
    )

    fun openChats() {
        Ui.tap(withId(R.id.homeNavigationChats))
        Ui.waitFor(withId(R.id.chatsRecyclerView))
    }

    fun isChatListed(name: String) = Ui.appears(chatTitle(name))

    fun openChat(name: String) = Ui.tap(allOf(
        withId(DesignR.id.sendMessageButton),
        isDescendantOfA(allOf(
            isAssignableFrom(ForoomChatCardView::class.java),
            hasDescendant(chatTitle(name))
        ))
    ))

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

    fun search(name: String) {
        Ui.enterText(R.id.searchChatInput, name)
        Ui.waitFor(withId(R.id.chatsRecyclerView))
    }

    fun assertChatListed(name: String) {
        Ui.waitFor(allOf(
            withId(DesignR.id.chatTitleTextView),
            isDescendantOfA(withId(R.id.chatsRecyclerView)),
            withText(name)
        ))
    }
}
