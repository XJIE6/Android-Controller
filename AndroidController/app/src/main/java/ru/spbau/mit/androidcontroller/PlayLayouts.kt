package ru.spbau.mit.androidcontroller

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import org.jetbrains.anko.custom.style


object PlayLayouts {
    private fun addChangeDialog(b: Button, ctx: Context, screenName: String) {
        val playLayout = commands[screenName]
        b.onClick {
            when (ctx) {
                is SettingsActivity -> {
                    val editChange = EditText(ctx)
                    editChange.setText(playLayout?.getCommand(b.id) ?: "")
                    AlertDialog
                            .Builder(ctx)
                            .setTitle("Change command")
                            .setMessage("Enter the command of the button ${b.text}")
                            .setView(editChange)
                            .setNeutralButton("Ok", { _, _ ->
                                playLayout?.setCommand(b.id, editChange.text.toString())
                            })
                            .show()

                }
                is PlayActivity -> {
                    ctx.toast(playLayout?.getCommand(b.id) ?: ":(")
                }
            }
        }
    }

    private fun buttonBg(ctx: Context) = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 6f
        setStroke(2, R.color.playButtons)
        setColor(ctx.getColor(R.color.playButtons))
    }

    private var commands: Map<String, PlayLayout> = hashMapOf(
            Pair("Screen1", PlayLayout("Screen1", hashMapOf(Pair(0, "a")))),
            Pair("Screen2", PlayLayout("Screen2", hashMapOf(Pair(1, "b"), Pair(2, "c"))))
    )

    private var layouts: Map<String, (ViewManager.() -> LinearLayout)> = hashMapOf(
            Pair("Screen1", fun ViewManager.() = linearLayout {
                    val b1 = button("Push") {
                        width = matchParent
                        height = matchParent
                        background = buttonBg(context)
                        id = 0
                    }.lparams {
                        weight = 1f
                        margin = dip(10)
                    }
                    addChangeDialog(b1, context, "Screen1")
                }
            ),
            Pair("Screen2", fun ViewManager.() = linearLayout {
                    addChangeDialog(button("Left") {
                        background = buttonBg(context)
                        width = matchParent
                        height = matchParent
                        id = 1
                    }.lparams {
                        margin = dip(10)
                        weight = 1f
                    }, context, "Screen2")
                    addChangeDialog(button("Right") {
                        background = buttonBg(context)
                        width = matchParent
                        height = matchParent
                        id = 2
                    }.lparams {
                        margin = dip(10)
                        weight = 1f
                    }, context, "Screen2")

                }
            )
    )

    fun getKeys(): Array<String> {
        return layouts.keys.sorted().toTypedArray()
    }

    fun getLayout(name: String): (ViewManager.() -> LinearLayout)? {
        return layouts[name]
    }
}