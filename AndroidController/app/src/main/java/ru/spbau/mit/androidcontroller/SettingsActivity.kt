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
        val playLayoutName = ui.owner.intent.extras.getCharSequence(resources.getString(R.string.play_layout)).toString()
        val customize = PlayLayouts.getLayout(playLayoutName)
        if (customize != null) {
            verticalLayout {
                customize().lparams(width = matchParent, height = matchParent, weight = 1f)
                linearLayout {
                    button("Play")
                            .lparams {
                                width = matchParent
                                height = wrapContent
                                this.gravity = Gravity.BOTTOM
                            }
                            .onClick {
                                toast("Play!")
                                MainActivity.connection.sendSettings(PlayLayouts.commands[playLayoutName]!!.commands) // TODO: strict cast
                                startActivity<PlayActivity>(resources.getString(R.string.play_layout) to playLayoutName)
                            }
                }.lparams(width = matchParent, height = wrapContent)
            }
        } else {
            throw ExceptionInInitializerError("Couldn't find screen with the name $playLayoutName")
        }
    }
}
