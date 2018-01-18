package ru.spbau.mit.androidcontroller

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

object PlayLayouts {
    private var layouts: Map<String, (ui: Activity) -> LinearLayout> = hashMapOf(
            Pair("Screen1", {ui -> ui.linearLayout {
                button("Push") {
                    width = matchParent
                    height = matchParent
                }.onClick { ui.toast("Push!") }
            } }),
            Pair("Screen2", {ui -> ui.linearLayout {
                button("Left") {
                    width = matchParent
                    height = matchParent
                }.onClick { ui.toast("Left") }
                button("Right") {
                    width = matchParent
                    height = matchParent
                }.onClick { ui.toast("Right") }
            }})
    )

    fun getKeys(): Array<String> {
        return layouts.keys.sorted().toTypedArray()
    }

    fun getLayout(name: String, ctx: Activity): LinearLayout? {
        return layouts[name]?.invoke(ctx)
    }
}