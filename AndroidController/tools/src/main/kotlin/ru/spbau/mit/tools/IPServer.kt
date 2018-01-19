package ru.spbau.mit.tools

import java.io.DataInputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException

class IPServer(val factory: () -> Handler) {
    val server = ServerSocket(12345)
    var isOpen = true

    fun start() {

        Thread({
            server.soTimeout = 100000 // Timeout for accepting
            while (isOpen) {
                try {
                    println("accepting")
                    val connection = server.accept()
                    Thread( {
                        println("accepted")
                        IPConnection(connection, factory.invoke()).start()
                    }).start()
                }
                catch (e : SocketTimeoutException) {
                    println("timeout")
                    continue
                }

            }
        }).start()
    }
    fun stop() {
        isOpen = false
    }
}

class IPConnection(socket : Socket, val handler : Handler) {
    val input = DataInputStream(socket.getInputStream())
    fun start() {
        var msg = input.readInt()
        while(msg != SocketConnection.END_CONNECTION) {
            if (msg == SocketConnection.START_SETTINGS) {
                val n = input.readInt()
                handler.onSetting(Array(n, {i -> input.readUTF()}))
            }
            else {
                handler.onClick(msg)
            }
            msg = input.readInt()
        }
        handler.onClose()
    }
}