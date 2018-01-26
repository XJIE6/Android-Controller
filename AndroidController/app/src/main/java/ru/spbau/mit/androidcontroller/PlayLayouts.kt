package ru.spbau.mit.androidcontroller

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewManager
import android.widget.*
import org.jetbrains.anko.*

interface PlayLayout {
    val DEFAULT_COMMANDS: Array<String>
    val ELEMENTS_COUNT: Int

    fun buttonStyle(ctx: Context) = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 6f
        setStroke(2, R.color.playButtons)
        setColor(ctx.getColor(R.color.playButtons))
    }

    fun TableRow.createButton(id_: Int, text: String): Button {
        val b = button(text) {
            background = PlayLayoutLeftRight.buttonStyle(context)
            id = id_
        }
        val layoutParams = TableRow.LayoutParams(matchParent, matchParent, 1f)
        b.layoutParams = layoutParams
        return b
    }

    fun TableRow.createView(): TextView {
        val view = textView { }
        val layoutParams = TableRow.LayoutParams(matchParent, matchParent, 1f)
        view.layoutParams = layoutParams
        return view
    }

    fun TableLayout.createRow(init: (@AnkoViewDslMarker _TableRow).() -> Unit): TableRow {
        val tr = tableRow { init() }
        val layoutParams = TableLayout.LayoutParams(matchParent, matchParent, 1f)
        tr.layoutParams = layoutParams
        return tr
    }

    fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout
}

object PlayLayoutOneButton : PlayLayout {
    override val DEFAULT_COMMANDS = arrayOf<String>("+S h -S e l l o P +S w -S o r l d")
    override val ELEMENTS_COUNT = 1

    override fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout =
            fun ViewManager.() = tableLayout {
                createRow {
                    id = R.id.layout_one_button
                    val elementId = 0
                    val b1 = this.createButton(elementId, "Push")
                    b1.setOnClickListener(lambdas[elementId])
                }
            }
}

object PlayLayoutLeftRight : PlayLayout {
    override val DEFAULT_COMMANDS = arrayOf<String>("+S h -S e l o L l R", "+S h -S e [ l ] 3 B o")
    override val ELEMENTS_COUNT = 2

    override fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout =
            fun ViewManager.() = tableLayout {
                createRow {
                    id = R.id.layout_left_right
                    var elementId = 0
                    listOf("Left", "Right").forEach {
                        createButton(elementId, it).setOnClickListener(lambdas[elementId++])
                    }
                }
            }
}

object PlayLayoutArrows : PlayLayout {
    override val DEFAULT_COMMANDS = arrayOf("U", "L", "D", "R")
    override val ELEMENTS_COUNT = 4

    override fun build(context: Context, lambdas: Array<(View) -> Unit>): ViewManager.() -> LinearLayout =
            fun ViewManager.() = linearLayout {
                id = R.id.arrows
                var elementId = 0
                tableLayout {
                    createRow {
                        createView()
                        createButton(elementId, "Up").setOnClickListener(lambdas[elementId++])
                        createView()
                    }
                    createRow {
                        listOf("Left", "Down", "Right").forEach {
                            createButton(elementId, it).setOnClickListener(lambdas[elementId++])
                        }
                    }
                }.lparams {
                    weight = 1f
                    width = matchParent
                    height = matchParent
                }
            }
}

object PlayLayoutStorage {
    val layouts: ArrayList<PlayLayout> = arrayListOf(PlayLayoutOneButton, PlayLayoutLeftRight, PlayLayoutArrows)
}
