package ru.spbau.mit.androidcontroller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.resources
import org.jetbrains.anko.setContentView

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayActivityUI().setContentView(this)
    }
}

class PlayActivityUI : AnkoComponent<PlayActivity> {
    override fun createView(ui: AnkoContext<PlayActivity>): LinearLayout = with(ui) {
        val playLayoutPos = ui.owner.intent.extras.getInt(resources.getString(R.string.play_layout))
        val customize = ScreenStorage.screens[playLayoutPos].buildScreenPlay(ui.owner, ui.owner.applicationContext)
        customize()
    }
}
