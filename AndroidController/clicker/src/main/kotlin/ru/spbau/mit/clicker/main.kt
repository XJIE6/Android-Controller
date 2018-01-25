package ru.spbau.mit.clicker

import ru.spbau.mit.tools.connection.Handler
import ru.spbau.mit.tools.connection.IPServer
import ru.spbau.mit.tools.lang.*
import java.awt.Robot
import java.net.NetworkInterface

fun getAddress() : String {
    return NetworkInterface.getNetworkInterfaces().toList()
            .flatMap { it.inetAddresses.toList()
                    .filter { it.address.size == 4 }
                    .filter { !it.isLoopbackAddress }
                    .filter { it.address[0] != 10.toByte() }
                    .map { it.hostAddress }
            }.first() }

fun main(args: Array<String>) {

    val server = IPServer({
        object : Handler {
            var array: Array<() -> Unit> = arrayOf()
            val parser = KeyParser()
            val robot = Robot()

            private fun eval(cmd : Cmd): Unit = when(cmd) {
                is Key -> { robot.keyPress(cmd.id)
                    robot.keyRelease(cmd.id)}
                is Press -> robot.keyPress(cmd.id)
                is Release -> robot.keyRelease(cmd.id)
                is Repeat -> repeat(cmd.count, {cmd.cmds.forEach({eval(it)})})
            }

            override fun onSetting(arr: Array<String>) {
                array = arr.map { it -> {eval(parser.parse(it))} }.toTypedArray()
            }

            override fun onClick(cmd: Int) {
                if (array.size > cmd) {
                    array[cmd]()
                }
            }

            override fun onClose() {
            }
        }
    })
    println(getAddress() + ':' + server.getPort())
    server.start()
    readLine()
    server.stop()
}