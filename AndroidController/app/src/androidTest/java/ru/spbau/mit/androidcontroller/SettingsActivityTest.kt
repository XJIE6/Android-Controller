package ru.spbau.mit.androidcontroller

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.view.View
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsActivityEspressoTest {

    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<SettingsActivity> =
            object : IntentsTestRule<SettingsActivity>(SettingsActivity::class.java) {
                override fun getActivityIntent(): Intent {
                    val targetContext = InstrumentationRegistry.getInstrumentation()
                            .targetContext
                    val result = Intent(targetContext, SettingsActivity::class.java)
                    result.putExtra(targetContext.getString(R.string.play_layout), 0)
                    return result
                }
            }

    @Test
    fun launchTest() {
        val playLayout = mActivityRule.activity.findViewById<View>(R.id.layout_one_button)
        val playButton = mActivityRule.activity.findViewById<View>(R.id.play_button)
        Assert.assertNotNull(playButton)
        Assert.assertNotNull(playLayout)
    }

    @Test
    fun ensureTextChangesWork() {
        ScreenStorage.screens[0].commands[0] = ""
        val textToWrite = "Lol"
        Espresso.onView(ViewMatchers.withId(0))
                .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.change_command))
                .perform(ViewActions.typeText(textToWrite), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withText(R.string.ok))
                .perform(click())

        assertEquals(ScreenStorage.screens[0].commands[0], textToWrite)
    }

    @Test
    fun ensureNextActivity() {
        MainActivity.connection = ConnectionMock()
        Espresso.onView(ViewMatchers.withId(R.id.play_button)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(PlayActivity::class.java.name))
    }
}