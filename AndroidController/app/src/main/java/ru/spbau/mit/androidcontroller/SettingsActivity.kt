package ru.spbau.mit.androidcontroller

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsActivityUI().setContentView(this)
    }
}

class CustomRootSettingsLayout<in T : Activity>(private val customize: AnkoContext<T>.() -> Unit = {}) : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>) = with(ui) {
        val playLayoutName = ui.owner.intent.extras.getCharSequence(resources.getString(R.string.play_layout)).toString()
        verticalLayout {
            customize()
            button("Play").onClick {
                toast("Play!")
            }
        }
    }
}

class SettingsActivityUI: AnkoComponent<SettingsActivity> {
    override fun createView(ui: AnkoContext<SettingsActivity>) = ui.owner.verticalLayout {
        val playLayoutName = ui.owner.intent.extras.getCharSequence(resources.getString(R.string.play_layout)).toString()
        PlayLayouts.getLayout(playLayoutName, ui.owner)
        button("Play").onClick {
            ui.owner.toast("Play!")
        }
    }
}
