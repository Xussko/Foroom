package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import com.example.foroom.pages.CreateChatPage
import com.example.foroom.pages.RegistrationPage

class ConversationSteps {
    private val loginSteps = LoginSteps()
    private val chatsPage = ChatsPage()
    private val conversationPage = ConversationPage()
    private val registrationPage = RegistrationPage()
    private val createChatPage = CreateChatPage()

    fun registerAccount(username: String, password: String) {
        loginSteps.establishLoginScreen()
        registrationPage.open()
        registrationPage.enterUsername(username)
        registrationPage.enterPassword(password)
        registrationPage.selectAvatar()
        registrationPage.submit()
        chatsPage.assertHomeDisplayed()
    }

    fun switchAccount(username: String, password: String) {
        loginSteps.establishLoginScreen()
        loginSteps.logIn(username, password)
    }

    fun prepareChat(title: String) {
        chatsPage.openChats()
        chatsPage.search(title)
        if (!chatsPage.isChatListed(title)) {
            createChatPage.open()
            createChatPage.enterName(title)
            createChatPage.selectImage()
            createChatPage.create()
            conversationPage.assertOpen(title)
            conversationPage.close()
        }
    }

    fun openChat(title: String) {
        chatsPage.openChats()
        chatsPage.search(title)
        chatsPage.openChat(title)
        conversationPage.assertOpen(title)
    }

    fun sendAndVerify(text: String, sender: String) {
        conversationPage.send(text)
        conversationPage.assertMessage(text, sender)
    }

    fun reopenAndVerify(title: String, text: String, sender: String) {
        conversationPage.close()
        openChat(title)
        conversationPage.assertMessage(text, sender)
    }

    fun assertInOlderHistory(text: String, sender: String) =
        conversationPage.assertMessageOutsideViewport(text, sender)

    fun readOlderMessage(text: String, sender: String) {
        conversationPage.assertMessageOutsideViewport(text, sender)
        conversationPage.findOlderMessage(text, sender)
    }

    fun verifyMessage(text: String, sender: String) = conversationPage.assertMessage(text, sender)
}
