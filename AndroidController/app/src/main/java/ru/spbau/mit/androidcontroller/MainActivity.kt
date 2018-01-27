package ru.spbau.mit.androidcontroller

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.coroutines.onClick
import ru.spbau.mit.tools.connection.AppConnection


class MainActivity : AppCompatActivity() {
    lateinit var connection: AppConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = applicationContext as MyApplication
        connection = app.connection
        ScreenStorage.loadData(this)
        MainActivityUI().setContentView(this)
    }

    override fun onResume() {
        super.onResume()
        connection.close()
    }

    override fun onStop() {
        super.onStop()
        ScreenStorage.saveData(this)
    }
}

class MainActivityUI : AnkoComponent<MainActivity> {

    private fun goToMenu(activity: MainActivity) {
        activity.startActivity<MenuActivity>()
    }

    private fun hideKeyboard(activity: MainActivity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun enableAll(elements: List<View>, enabled: Boolean) {
        elements.forEach({ it.isEnabled = enabled })
    }

    private fun tryToConnect(code: String, activity: MainActivity, elements: List<View>) {
        launch(UI) {
            enableAll(elements, false)
            val isConnected = bg { activity.connection.connect(code) }.await()
            enableAll(elements, true)
            if (isConnected) {
                goToMenu(activity)
            } else {
                activity.toast("Couldn't connect to server. Check IP and port.")
            }
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            id = R.id.main_layout
            gravity = Gravity.CENTER
            verticalLayout {
                val code = editText {
                    id = R.id.ip_port
                    hintResource = R.string.hint_text
                    inputType = InputType.TYPE_CLASS_TEXT
                    singleLine = true
                    imeOptions = EditorInfo.IME_ACTION_DONE
                }.lparams {
                    bottomMargin = dip(10)
                }

                val connectButton = button {
                    id = R.id.button_connect
                    textResource = R.string.connect_button

                }.lparams(width = matchParent, height = dip(60))

                connectButton.onClick {
                    tryToConnect(code.text.toString(), ui.owner, arrayListOf<View>(connectButton, code))
                }


                code.setOnEditorActionListener { _, actionId, _ ->
                    var handled = false
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        hideKeyboard(ui.owner)
                        tryToConnect(code.text.toString(), ui.owner, arrayListOf<View>(connectButton, code))
                        handled = true
                    }
                    handled
                }

            }.lparams(width = wrapContent)
        }
    }
}
