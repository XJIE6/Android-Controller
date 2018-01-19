package ru.spbau.mit.androidcontroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import ru.spbau.mit.tools.SocketConnection

class MainActivity : AppCompatActivity() {

    val connection = SocketConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread({ connection.connect("")
        val layout = verticalLayout {
            val name = editText()
            button("Setup") {
                onClick {
                    connection.sendSettings(arrayOf(name.text.toString()))
                }
            }
            button("Click") {
                onClick {
                    connection.sendCommand(0)
                }
            }
            button("Exit") {
                onClick {
                    connection.close()
                }
            }
        }
        setContentView(layout)
        }).start()
    }
}
