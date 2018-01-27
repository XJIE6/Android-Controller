package ru.spbau.mit.tools.connection


import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.concurrent.thread

class SocketConnection : AppConnection {

    private val socket = Socket()
    private lateinit var out : BufferedWriter

    override fun connect(params : String) : Boolean {
        val thread = thread {
            val ipPort = params.split(":")
            socket.connect(InetSocketAddress(ipPort[0], ipPort[1].toInt()))
        }
        thread.join()
        if (socket.isConnected) {
            out = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            return true
        }
        return false
    }

    override fun sendSettings(settingList: Array<String>) {
        val thread = thread {
            out.write(Protocol.START_SETTINGS)
            out.write(settingList.size.toString())
            settingList.forEach { out.write(it) }
            out.flush()
        }
        thread.join()
    }

    override fun sendCommand(command: Int) {
        val thread = thread {
            out.write(command)
            out.flush()
        }
        thread.join()
    }

    override fun close() {
        val thread = thread {
            if (socket.isConnected) {
                out.write(Protocol.END_CONNECTION)
                out.flush()
            }
        }
        thread.join()
    }
}