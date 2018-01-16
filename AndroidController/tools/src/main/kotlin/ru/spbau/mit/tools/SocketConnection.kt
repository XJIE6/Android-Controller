package ru.spbau.mit.tools

import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.net.Socket

class SocketConnection : AppConnection {

    companion object {
        const val START_SETTINGS = -1
    }

    val socket = Socket()
    lateinit var out : ObjectOutputStream

    override fun connect(params: String): Boolean {
        try {
            socket.connect(InetSocketAddress("10.0.2.2", 12345))
            out = ObjectOutputStream(socket.getOutputStream())
        }
        catch (e: Throwable) {
            println(e.message)
        }
        return socket.isConnected
    }

    override fun sendSettings(settingList: Array<String>) =
            Thread({
                    out.writeInt(START_SETTINGS)
                    out.writeInt(settingList.size)
                    settingList.forEach { out.writeUTF(it) }
                    out.flush()}).start()

    override fun sendCommand(command: Int) =
            Thread({out.writeInt(command)
                    out.flush()}).start()
}