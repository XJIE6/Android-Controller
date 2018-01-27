package ru.spbau.mit.androidcontroller

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import android.view.View
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuActivityEspressoTest {

    @Rule
    @JvmField
    var mActivityRule = IntentsTestRule(MenuActivity::class.java)

    @Test
    fun launchTest() {
        val listView = mActivityRule.activity.findViewById<View>(R.id.menu)
        val addButton = mActivityRule.activity.findViewById<View>(R.id.add_button)
        Assert.assertNotNull(listView)
        Assert.assertNotNull(addButton)
    }

    @Test
    fun ensureNextActivity() {
        Espresso.onView(ViewMatchers.withText("Screen1")).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(SettingsActivity::class.java.name))
    }
}