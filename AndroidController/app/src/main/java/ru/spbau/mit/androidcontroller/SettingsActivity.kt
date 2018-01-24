package ru.spbau.mit.androidcontroller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsActivityUI().setContentView(this)
    }
}

class SettingsActivityUI: AnkoComponent<SettingsActivity> {
    override fun createView(ui: AnkoContext<SettingsActivity>) : View = with(ui) {
        val playLayoutPos = ui.owner.intent.extras.getInt(resources.getString(R.string.play_layout))
        val customize = ScreenStorage.screens[playLayoutPos].buildScreenPreview(ui.owner)
        verticalLayout {
            customize().lparams(width = matchParent, height = matchParent, weight = 1f)
            linearLayout {
                button("Play")
                        .lparams {
                            id = R.id.play_button
                            width = matchParent
                            height = wrapContent
                            this.gravity = Gravity.BOTTOM
                        }
                        .onClick {
                            toast("Play!")
//                            MainActivity.connection.sendSettings(ScreenStorage.screens[playLayoutPos].commands)
                            startActivity<PlayActivity>(resources.getString(R.string.play_layout) to playLayoutPos)
                        }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }
}
