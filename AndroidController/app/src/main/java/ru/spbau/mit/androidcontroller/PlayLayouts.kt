package ru.spbau.mit.androidcontroller

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.abc_dialog_title_material.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.EditText


object PlayLayouts {
    private var commands: Map<String, PlayLayout> = hashMapOf(
            Pair("Screen1", PlayLayout("Screen1", hashMapOf(Pair(0, "a")))),
            Pair("Screen2", PlayLayout("Screen2", hashMapOf(Pair(1, "b"), Pair(2, "c"))))
    )

    private var layouts: Map<String, (ViewManager.() -> Unit)> = hashMapOf(
            Pair("Screen1", fun ViewManager.() {
                linearLayout {
                    val b1 = button("Push") {
                        width = matchParent
                        height = matchParent
                        id = 0
                    }
                    b1.onClick {
                        val ctx = getContext()
                        when(ctx) {
                            is SettingsActivity -> {
                                val editChange = EditText(ctx)
                                editChange.setText(commands["Screen1"]?.getCommand(b1.id) ?: "")
                                AlertDialog
                                        .Builder(ctx)
                                        .setTitle("Change command")
                                        .setMessage("Enter the command of the button ${b1.text}")
                                        .setView(editChange)
                                        .setNeutralButton("Ok", DialogInterface.OnClickListener { dialogInterface, _ ->
                                            commands["Screen1"]?.setCommand(b1.id, editChange.text.toString())
                                        })
                                        .show()

                            }
                            is PlayActivity -> {
                                ctx.toast(commands["Screen1"]?.getCommand(b1.id) ?: ":(")
                            }
                        }
                    }
                }
            }),
            Pair("Screen2", fun ViewManager.()  {
                linearLayout {
                    button("Left") {
                        width = matchParent
                        height = matchParent
                    }
                    button("Right") {
                        width = matchParent
                        height = matchParent
                    }
                }
            })
    )

    fun getKeys(): Array<String> {
        return layouts.keys.sorted().toTypedArray()
    }

    fun getLayout(name: String): (ViewManager.() -> Unit)? {
        return layouts[name]
    }
}