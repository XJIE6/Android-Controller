package ru.spbau.mit.clicker

import ru.spbau.mit.tools.Handler
import ru.spbau.mit.tools.IPServer
import ru.spbau.mit.tools.WordParser
import java.net.NetworkInterface

fun getAddress(): String {
    return NetworkInterface.getNetworkInterfaces().toList()
            .flatMap { it.inetAddresses.toList()
                    .filter { it.address.size == 4 }
                    .filter { !it.isLoopbackAddress }
                    .filter { it.address[0] != 10.toByte() }
                    .map { it.hostAddress }
            }.first() }

fun main(args: Array<String>) {

    val server = IPServer({object : Handler {
        var array : Array<() -> Unit> = arrayOf()
        val parser = WordParser()
        override fun onSetting(arr: Array<String>) {
            println("setted")
            array = arr.map { it -> parser.parce(it) }.toTypedArray()
        }

        override fun onClick(cmd: Int) {
            println("clicked")
            if (array.size > cmd) {
                array[cmd]()
            }
            else {
                println("wrong msg")
            }
        }

        override fun onClose() {
        }
    }})
    println(getAddress() + ':' + server.getPort())
    server.start()
}