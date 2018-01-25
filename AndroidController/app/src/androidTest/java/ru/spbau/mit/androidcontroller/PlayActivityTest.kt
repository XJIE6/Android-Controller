package ru.spbau.mit.androidcontroller

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import android.view.View
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.action.ViewActions.click
import junit.framework.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class PlayActivityEspressoTest {

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
        Assert.assertNotNull(playLayout)
    }
}