package com.example.dogsapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

class MainActivityIntentSendTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<SplashScreenActivity>()


    @Test
    fun testButtonShare() {
        onView(withId(R.id.btn_share)).perform(click())
    }


    @Test
    fun testShareButton_expectIntentChooser() {
        Intents.init()
        val expected = allOf(hasAction(Intent.ACTION_SEND))
        onView(withId(R.id.btn_share)).perform(click())
        intended(expected)
        Intents.release()
    }


}