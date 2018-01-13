package ru.spbau.mit.androidcontroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import ru.spbau.mit.tools.SocketConnection
import ru.spbau.mit.tools.WordParser

class MainActivity : AppCompatActivity() {

    val connection = SocketConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread({ connection.connect("")
        val layout = verticalLayout {
            val name = editText()
            val parser = WordParser()
//            button("Say Hello") {
//                onClick { toast("Hello, ${name.text}!") }
//            }
            button("Say Hello") {
                onClick {
                    connection.sendSettings(arrayOf(parser.parce(name.text.toString())))
                    connection.sendCommand(0)
                }
            }
        }
        setContentView(layout)
        }).start()
    }
}
