package ru.spbau.mit.tools.connection


import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SocketConnection : AppConnection {

    private val socket = Socket()
    private lateinit var out: DataOutputStream
    private val threadPool = Executors.newSingleThreadExecutor()
    override fun connect(params: String): Boolean {
        threadPool.submit {
            val ipPort = params.split(":")
            socket.connect(InetSocketAddress(ipPort[0], ipPort[1].toInt()))
        }
        threadPool.awaitTermination(1, TimeUnit.SECONDS)
        if (socket.isConnected) {
            out = DataOutputStream(socket.getOutputStream())
            return true
        }
        return false
    }

    override fun sendSettings(settingList: Array<String>) {
        threadPool.submit {
            out.writeInt(Protocol.START_SETTINGS)
            out.writeInt(settingList.size)
            settingList.forEach { out.writeUTF(it) }
            out.flush()
        }
    }

    override fun sendCommand(command: Int) {
        threadPool.submit {
            out.writeInt(command)
            out.flush()
        }
    }

    override fun close() {
        threadPool.submit {
            if (socket.isConnected) {
                out.writeInt(Protocol.END_CONNECTION)
                out.flush()
            }
        }
    }
}