package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ConversationSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ConversationWithMyFriendTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val suffix = UUID.randomUUID().toString().take(8)
    private val userA = "android4_a_$suffix"
    private val userB = "android4_b_$suffix"
    private val password = "Training4Test123!"
    private val fullName = InstrumentationRegistry.getArguments()
        .getString("studentFullName") ?: "Nikoloz Khuskivadze"
    private val ownChat = "$fullName Android4"
    private val sharedChat = "something Android4"
    private val steps = ConversationSteps()

    @Before
    fun prepareLocalAccountsAndChats() {
        steps.registerAccount(userB, password)
        steps.registerAccount(userA, password)
        steps.switchAccount(userA, password)
        listOf("johnWeek", ownChat, sharedChat).forEach(steps::prepareChat)
    }

    @Test
    fun sendDrinkInvitationInJohnWeekAndReadItAfterReopening() {
        val invitation = "let's go for a drink [$suffix]"
        steps.openChat("johnWeek")
        steps.sendAndVerify(invitation, userA)
        steps.reopenAndVerify("johnWeek", invitation, userA)
    }

    @Test
    fun askAboutFavouriteAutomationAcademyModuleInMyOwnChat() {
        val question = "Which module do you like most in the Automation Academy? [$suffix]"
        steps.openChat(ownChat)
        steps.sendAndVerify(question, userA)
    }

    @Test
    fun readOlderGreetingAsAnotherAccountAndReplyInTheSharedChat() {
        val greeting = "Hello, my friend! [$suffix]"
        val reply = "Hello User A, nice to hear from you! [$suffix]"
        steps.openChat(sharedChat)
        steps.sendAndVerify(greeting, userA)
        repeat(25) { index ->
            steps.sendAndVerify("Conversation update ${index + 1} [$suffix]", userA)
        }
        steps.assertInOlderHistory(greeting, userA)
        steps.switchAccount(userB, password)
        steps.openChat(sharedChat)
        steps.readOlderMessage(greeting, userA)
        steps.sendAndVerify(reply, userB)
        steps.switchAccount(userA, password)
        steps.openChat(sharedChat)
        steps.verifyMessage(reply, userB)
    }
}
