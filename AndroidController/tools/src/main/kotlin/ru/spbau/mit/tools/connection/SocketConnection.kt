package ru.spbau.mit.tools.connection


import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.concurrent.thread

class SocketConnection : AppConnection {

    private val socket = Socket()
    private lateinit var out : DataOutputStream

    override fun connect(params : String) : Boolean {
        val thread = thread {
            val ipPort = params.split(":")
            socket.connect(InetSocketAddress(ipPort[0], ipPort[1].toInt()))
        }
        thread.join()
        if (socket.isConnected) {
            out = DataOutputStream(socket.getOutputStream())
            return true
        }
        return false
    }

    override fun sendSettings(settingList: Array<String>) {
        val thread = thread {
            out.writeInt(Protocol.START_SETTINGS)
            out.writeInt(settingList.size)
            settingList.forEach { out.writeUTF(it) }
            out.flush()
        }
        thread.join()
    }

    override fun sendCommand(command: Int) {
        val thread = thread {
            out.writeInt(command)
            out.flush()
        }
        thread.join()
    }

    override fun close() {
        val thread = thread {
            if (socket.isConnected) {
                out.writeInt(Protocol.END_CONNECTION)
                out.flush()
            }
        }
        thread.join()
    }
}