package ru.spbau.mit.androidcontroller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainActivity : AppCompatActivity() {

//    val connection = SocketConnection()

    // function to hide keyboard
    fun hideKeyboard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun goToMenu() {
        startActivity(intentFor<MenuActivity>().singleTop())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread(
                {
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

                    val layout = verticalLayout {
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
                                    hideKeyboard()
                                    toast("Done!")
                                    goToMenu()
                                    handled = true
                                }
                                handled
                            }


                            val connectButton = button() {
                                textResource = R.string.connect_button

                                onClick {
                                    toast("Click!")
                                    goToMenu()
                                }
                            }.lparams(width=matchParent, height=dip(60))
                        }.lparams(width=wrapContent)
                    }
                    setContentView(layout)
                }).start()
    }
}
