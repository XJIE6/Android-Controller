package ru.spbau.mit.androidcontroller

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() {

//    val connection = SocketConnection()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//                    connection.connect("")
//                    val layout = verticalLayout {
//                        val name = editText()
//                        button("Setup") {
//                            onClick {
////                                connection.sendSettings(arrayOf(name.text.toString()))
//                            }
//                        }
//                        button("Click") {
//                            onClick {
//                                //                    connection.sendCommand(0)
//                            }
//                        }
//                    }

        MainActivityUI().setContentView(this)
    }
}

class MainActivityUI: AnkoComponent<MainActivity> {
    private fun goToMenu(activity: MainActivity) {
        activity.startActivity<MenuActivity>()
    }

    private fun hideKeyboard(activity: MainActivity) {
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            gravity = Gravity.CENTER
            val form = verticalLayout {
                val code = editText() {
                    hintResource = R.string.hint_text
                    inputType = InputType.TYPE_CLASS_TEXT
                    singleLine = true
                    imeOptions = EditorInfo.IME_ACTION_DONE
                }.lparams {
                    bottomMargin = dip(10)
                }

                code.setOnEditorActionListener { v, actionId, event ->
                    var handled = false
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        hideKeyboard(ui.owner)
                        toast("Done!")
                        goToMenu(ui.owner)
                        handled = true
                    }
                    handled
                }


                button() {
                    textResource = R.string.connect_button

                    onClick {
                        toast("Click!")
                        goToMenu(ui.owner)
                    }
                }.lparams(width=matchParent, height=dip(60))
            }.lparams(width=wrapContent)
        }
    }

}
