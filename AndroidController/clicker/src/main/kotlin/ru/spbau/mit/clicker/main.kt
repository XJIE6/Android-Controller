package ru.spbau.mit.clicker

import ru.spbau.mit.tools.SocketConnection
import ru.spbau.mit.tools.WordParser
import java.io.ObjectInputStream
import java.net.ServerSocket

fun main(args: Array<String>) {
    println("waiting for accepting")
    val socket = ServerSocket(12345).accept()
    val in_ = ObjectInputStream(socket.getInputStream())
    println("accepted")
    var array : Array<() -> Unit> = arrayOf()
    val parser = WordParser()
    var msg = in_.readInt()
    while (socket.isConnected) {
        if (msg == SocketConnection.START_SETTINGS) {
            val n = in_.readInt()
            array = Array(n, {i -> parser.parce(in_.readUTF())})
        }
        else {
            if (msg >= 0 && msg < array.size) {
                array[msg].invoke()
            }
            else {
                println("error")
            }
        }
        msg = in_.readInt()
    }

}