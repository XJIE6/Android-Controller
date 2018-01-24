package ru.spbau.mit.tools.connection


import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

class SocketConnection : AppConnection {

    private val socket = Socket()
    private lateinit var out : DataOutputStream

    override fun connect(params: String) {

        println("123456")
        Thread({
        try {

            println("1239999")
            println(params)
            val ipPort = params.split(":")
            println(ipPort)
            println("1239999")
            socket.connect(InetSocketAddress(ipPort[0], ipPort[1].toInt()))
            out = DataOutputStream(socket.getOutputStream())
        }
        catch (e: Throwable) {
            println(e.message)
        }}).start()
    }

    override fun sendSettings(settingList: Array<String>) =
            Thread({
                    out.writeInt(Protocol.START_SETTINGS)
                    out.writeInt(settingList.size)
                    settingList.forEach { out.writeUTF(it) }
                    out.flush()}).start()

    override fun sendCommand(command: Int) =
            Thread({
                out.writeInt(command)
                out.flush()}).start()

    override fun close() =
            Thread({
                if (socket.isConnected) {
                    out.writeInt(Protocol.END_CONNECTION)
                    out.flush()
                }
                //socket.close()
            }).start()
}