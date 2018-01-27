package ru.spbau.mit.tools.connection

import com.soywiz.korio.async.EventLoop
import com.soywiz.korio.async.spawn
import com.soywiz.korio.net.AsyncClient
import com.soywiz.korio.net.AsyncServer
import com.soywiz.korio.net.asyncSocketFactory
import com.soywiz.korio.stream.readLine
import kotlinx.coroutines.experimental.launch
import java.io.EOFException

class IPServer(private val factory: () -> Handler) {
    private lateinit var server: AsyncServer
    private var isOpen = true

    fun getPort(): Int {
        return server.port
    }

    fun init(address: String) = EventLoop.invoke {
        server = asyncSocketFactory.createServer(0, address)
    }

    fun start() = launch {
        EventLoop.invoke {
            while (true) {
                for (me in server.listen()) {
                    spawn(coroutineContext) {
                        IPConnection(me, factory()).start()
                    }
                }
            }
        }
    }

}

class IPConnection(private val socket: AsyncClient, private val handler: Handler) {

    suspend fun start() {
        println("connected")
        var msg = socket.readLine().toInt()
        try {
            while (msg != Protocol.END_CONNECTION) {
                if (msg == Protocol.START_SETTINGS) {
                    val n = socket.readLine().toInt()
                    handler.onSetting(Array(n, { _ -> socket.readLine() }))
                } else {
                    handler.onClick(msg)
                }
                msg = socket.readLine().toInt()
            }
        } catch (e: EOFException) {
            print("Connection is lost")
        } finally {
            handler.onClose()
            if (socket.connected) {
                socket.close()
            }
        }
    }
}