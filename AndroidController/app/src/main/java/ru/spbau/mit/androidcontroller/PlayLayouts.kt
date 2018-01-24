package ru.spbau.mit.androidcontroller

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import org.jetbrains.anko.*

interface PlayLayout {
    val ELEMENTS_COUNT: Int
    val DEFAULT_COMMANDS: Array<String>

    fun buttonStyle(ctx: Context) = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 6f
        setStroke(2, R.color.playButtons)
        setColor(ctx.getColor(R.color.playButtons))
    }

    fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout
    fun getElementCount(): Int {
        return ELEMENTS_COUNT
    }
    fun getDefaultCommands(): Array<String> {
        return DEFAULT_COMMANDS
    }
}

object PlayLayoutOneButton: PlayLayout {
    override val DEFAULT_COMMANDS = arrayOf("+S h -S e l l o P +S w -S o r l d")
    override val ELEMENTS_COUNT = 1

    override fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout =
        fun ViewManager.() = linearLayout {
            var elementId = 0
                val b1 = button("Push") {
                    background = buttonStyle(context)
                    id = elementId++
                }.lparams {
                    height = matchParent
                    weight = 1f
                    margin = dip(10)
                }
                b1.setOnClickListener(lambdas[elementId - 1])
        }
}

object PlayLayoutLeftRight: PlayLayout {
    override val DEFAULT_COMMANDS = arrayOf("+S h -S e l o L l R", "+S h -S e [ l ] 3 B o")
    override val ELEMENTS_COUNT = 2

    override fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout =
            fun ViewManager.() = linearLayout {
                var elementId = 0
                val b1 = button("Left") {
                    background = buttonStyle(context)
                    id = elementId++
                }.lparams {
                    width = matchParent
                    height = matchParent
                    margin = dip(10)
                    weight = 1f
                }
                b1.setOnClickListener(lambdas[elementId - 1])
                val b2 = button("Right") {
                    background = buttonStyle(context)
                    id = elementId++
                }.lparams {
                    width = matchParent
                    height = matchParent
                    margin = dip(10)
                    weight = 1f
                }
                b2.setOnClickListener(lambdas[elementId - 1])
        }
}

object PlayLayoutStorage {
    val layouts: ArrayList<PlayLayout> = arrayListOf(PlayLayoutOneButton, PlayLayoutLeftRight)
}
