package ru.spbau.mit.androidcontroller

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread({
            val layout = relativeLayout {
                    verticalLayout {
                        listView {
//                            button("Screen1")
//                            button("Settings")
                        }
                    }
                floatingActionButton {
                    imageResource = android.R.drawable.ic_input_add
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    gravity = Gravity.BOTTOM + Gravity.END
                    margin = dip(16)
                }.setOnClickListener { toast("Push!") }
            }
            setContentView(layout)
        }).start()
    }

}
