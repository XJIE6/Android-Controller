package ru.spbau.mit.tools.connection


import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SocketConnection : AppConnection {

    private val socket = Socket()
    private lateinit var out: BufferedWriter
    private val threadPool = Executors.newSingleThreadExecutor()
    override fun connect(params: String): Boolean {
        threadPool.submit {
            val ipPort = params.split(":")
            try {
                socket.connect(InetSocketAddress(ipPort[0], ipPort[1].toInt()))
            } catch (e: Exception) {
                print(e.toString())
            }
            val a = ipPort
        }
        threadPool.awaitTermination(1, TimeUnit.SECONDS)
        if (socket.isConnected) {
            out = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            return true
        }
        return false
    }

    override fun sendSettings(settingList: Array<String>) {
        threadPool.submit {
            out.write(Protocol.START_SETTINGS.toString() + '\n')
            out.write(settingList.size.toString() + '\n')
            settingList.forEach { out.write(it + '\n') }
            out.flush()
        }
    }

    override fun sendCommand(command: Int) {
        threadPool.submit {
            out.write(command.toString() + '\n')
            out.flush()
        }
    }

    override fun close() {
        threadPool.submit {
            if (socket.isConnected) {
                out.write(Protocol.END_CONNECTION.toString() + '\n')
                out.flush()
            }
        }
        threadPool.awaitTermination(1, TimeUnit.SECONDS)
    }
}