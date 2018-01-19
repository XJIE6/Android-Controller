package ru.spbau.mit.androidcontroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayActivityUI().setContentView(this)
    }
}

class PlayActivityUI: AnkoComponent<PlayActivity> {
    override fun createView(ui: AnkoContext<PlayActivity>): LinearLayout = with(ui) {
        val playLayoutName = ui.owner.intent.extras.getCharSequence(resources.getString(R.string.play_layout)).toString()
        val customize = PlayLayouts.getLayout(playLayoutName)
        if (customize != null) {
            linearLayout {
                customize()
            }
        } else {
//            TODO: Exception
            throw ExceptionInInitializerError("Couldn't find screen with the name $playLayoutName")
        }
    }
}
