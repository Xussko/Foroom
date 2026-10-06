package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps {
    private val createChatPage = CreateChatPage()
    private val chatsPage = ChatsPage()

    fun createChatAndVerify(name: String) {
        chatsPage.assertHomeDisplayed()
        createChatPage.open()
        createChatPage.enterName(name)
        createChatPage.selectImage()
        createChatPage.create()
        chatsPage.assertCreatedChatDisplayed(name)
    }

    fun findCreatedChatAndVerify(name: String) {
        chatsPage.closeChat()
        chatsPage.search(name)
        chatsPage.assertChatListed(name)
    }
}
