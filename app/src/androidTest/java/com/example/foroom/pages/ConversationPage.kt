package com.example.foroom.pages

import android.graphics.Rect
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.swiper
import com.example.foroom.helpers.Ui
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertFalse
import com.example.design_system.R as DesignR

class ConversationPage {
    fun assertOpen(title: String) {
        Ui.waitFor(allOf(
            withId(DesignR.id.chatNameTextView),
            isDescendantOfA(withId(R.id.chatHeaderView)),
            withText(title)
        ))
        Ui.waitFor(withId(R.id.messagesRecyclerView))
    }

    fun send(text: String) {
        Ui.enterText(R.id.messageInput, text)
        Ui.tap(withId(R.id.sendMessageButton))
        Ui.waitFor(allOf(Ui.input(R.id.messageInput), withText("")))
    }

    private fun message(text: String, sender: String) = allOf(
        withId(DesignR.id.messageTextView),
        withText(text),
        isDescendantOfA(allOf(
            withId(R.id.messageView),
            hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(sender))),
            isDescendantOfA(withId(R.id.messagesRecyclerView))
        ))
    )

    fun assertMessage(text: String, sender: String) {
        Ui.waitFor(message(text, sender))
    }

    fun assertMessageOutsideViewport(text: String, sender: String) {
        assertFalse("Message should have moved into older history: $text", Ui.isVisible(message(text, sender)))
    }

    fun swipeToOlderMessages() {
        val area = Rect()
        Ui.waitFor(withId(R.id.messagesRecyclerView)).check { view, failure ->
            if (failure != null) throw failure
            check(view.getGlobalVisibleRect(area))
            area.top += view.paddingTop
            area.bottom -= view.paddingBottom
        }
        check(area.height() > 0) { "Message viewport is empty" }
        swiper(area.top + area.height() / 5, area.bottom - area.height() / 5, 400)
    }

    fun findOlderMessage(text: String, sender: String, maxSwipes: Int = 40) {
        repeat(maxSwipes) {
            swipeToOlderMessages()
            if (Ui.isVisible(message(text, sender))) {
                assertMessage(text, sender)
                return
            }
        }
        throw AssertionError("Message from $sender was not visible after $maxSwipes swipes: $text")
    }

    fun close() = ChatsPage().closeChat()
}
