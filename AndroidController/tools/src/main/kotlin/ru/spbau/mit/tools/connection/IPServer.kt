package ru.spbau.mit.tools.connection

import java.io.DataInputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class IPServer(val factory: () -> Handler) {
    private val server = ServerSocket(0)
    private var isOpen = true

    fun getPort() : Int {
        return server.localPort
    }

    fun start() {
        thread {
            server.soTimeout = 100000 // Timeout for accepting
            while (isOpen) {
                try {
                    println("accepting")
                    println(server.localPort)
                    val connection = server.accept()
                    Thread({
                        println("accepted")
                        IPConnection(connection, factory.invoke()).start()
                    }).start()
                }
                catch (e : SocketTimeoutException) {
                    println("timeout")
                    continue
                }

            }
        }
    }

    fun stop() {
        isOpen = false
    }
}

class IPConnection(private val socket : Socket, private val handler : Handler) {

    private val input = DataInputStream(socket.getInputStream())

    fun start() {
        var msg = input.readInt()
        while(msg != Protocol.END_CONNECTION) {
            if (msg == Protocol.START_SETTINGS) {
                val n = input.readInt()
                handler.onSetting(Array(n, {_ -> input.readUTF()}))
            }
            else {
                handler.onClick(msg)
            }
            msg = input.readInt()
        }
        handler.onClose()
        socket.close()
    }
}