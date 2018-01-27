package ru.spbau.mit.androidcontroller

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.view.View
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @Rule
    @JvmField
    var mActivityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun launchTest() {
        val ipPortEditText = mActivityRule.activity.findViewById<View>(R.id.ip_port)
        val connectButton = mActivityRule.activity.findViewById<View>(R.id.button_connect)
        assertNotNull(ipPortEditText)
        assertNotNull(connectButton)
    }

    @Test
    fun ensureTextChangesWork() {
        onView(withId(R.id.ip_port))
                .perform(typeText("0.0.0.0:0"), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.ip_port)).check(matches(withText("0.0.0.0:0")))
    }

    @Test
    fun ensureNextActivity() {
        MainActivity.connection = ConnectionMock()
        onView(withId(R.id.button_connect)).perform(click())
        intended(IntentMatchers.hasComponent(MenuActivity::class.java.name))
    }
}