package ru.spbau.mit.clicker

import ru.spbau.mit.tools.Handler
import ru.spbau.mit.tools.IPServer
import ru.spbau.mit.tools.WordParser

fun main(args: Array<String>) {
    IPServer({object : Handler {
        var array : Array<() -> Unit> = arrayOf()
        val parser = WordParser()
        override fun onSetting(arr: Array<String>) {
            println("setted")
            array = arr.map { it -> parser.parce(it) }.toTypedArray()
        }

        override fun onClick(cmd: Int) {
            println("clicked")
            println(cmd)
            if (array.size > cmd) {
                array[cmd]()
            }
            else {
                println("wrong msg")
            }
        }

        override fun onClose() {
        }
    }}).start()
//    var array : Array<() -> Unit> = arrayOf()
//    val parser = WordParser()
//    var msg = in_.readInt()
//    while (socket.isConnected) {
//        if (msg == SocketConnection.START_SETTINGS) {
//            val n = in_.readInt()
//            array = Array(n, {i -> parser.parce(in_.readUTF())})
//        }
//        else {
//            if (msg >= 0 && msg < array.size) {
//                array[msg].invoke()
//            }
//            else {
//                println("error")
//            }
//        }
//        msg = in_.readInt()
//    }

}