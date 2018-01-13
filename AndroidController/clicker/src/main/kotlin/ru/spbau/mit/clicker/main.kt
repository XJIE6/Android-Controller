package ru.spbau.mit.clicker

import java.io.ObjectInputStream
import java.net.ServerSocket

fun main(args: Array<String>) {
    println("waiting for accepting")
    val socket = ServerSocket(12345).accept()
    val in_ = ObjectInputStream(socket.getInputStream())
    println("accepted")
    var array : Array<() -> Unit> = arrayOf()
    while (socket.isConnected) {
        val msg : Any = in_.readObject()
        if (msg is Array<*>) {
            array = msg as Array<() -> Unit>
        }
        else if (msg is Int) {
            array[msg].invoke()
        }
        else {
            TODO("unknown object")
        }
    }
}