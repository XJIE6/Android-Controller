package ru.spbau.mit.androidcontroller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import org.jetbrains.anko.*

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
            customize()
        } else {
            throw ExceptionInInitializerError("Couldn't find screen with the name $playLayoutName")
        }
    }
}
