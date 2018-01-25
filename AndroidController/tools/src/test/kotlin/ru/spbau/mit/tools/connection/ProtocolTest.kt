package ru.spbau.mit.tools.connection

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import kotlin.test.assertEquals


sealed class Command
data class Settings(val arr : Array<String>) : Command()
data class Click(val key : Int) : Command()
class Close : Command()

class TestHandler(val cmds : Array<Command>) : Handler{
    private var index = 0
    fun check() {
        assertEquals(index, cmds.size)
    }
    override fun onSetting(arr: Array<String>) {
        println("got setting")
        arr.forEach { println(it) }
        val cur = cmds[index] as Settings
        assertArrayEquals(arr, cur.arr)
        index++
    }

    override fun onClick(cmd: Int) {
        println("got click")
        println(cmd)
        val cur = cmds[index] as Click
        assertEquals(cmd, cur.key)
        index++
    }

    override fun onClose() {
        println("got close")
        cmds[index] as Close
        index++
    }
}

class TestHendlerBuilder(val cmds : Array<Command>) : () -> Handler {
    val handlerKeeper = ArrayList<TestHandler>()
    override fun invoke(): Handler {
        val handler = TestHandler(cmds)
        handlerKeeper.add(handler)
        return handler
    }
    fun check() {
        handlerKeeper.forEach { it.check() }
    }
}

class ProtocolTest {
    fun test(cmds : Array<Command>) {
        val handler = TestHendlerBuilder(cmds)
        val server = IPServer(handler)
        server.start()
        val client = SocketConnection()
        Thread.sleep(10000)
        client.connect("localhost" + ':' + server.getPort().toString())
        Thread.sleep(10000)
        cmds.forEach { when (it) {
            is Settings -> client.sendSettings(it.arr)
            is Click -> client.sendCommand(it.key)
            is Close -> client.close()
        } }
        Thread.sleep(100000)
        handler.check()
    }

    @Test
    fun simpleConnectionTest() {
        test(arrayOf(Close()))
    }
    @Test
    fun oneSessionConnection() {
        test(arrayOf(
                Settings(arrayOf("a", "b", "c")),
                Click(0),
                Click(1),
                Click(0),
                Click(1),
                Click(2),
                Close()))
    }
    @Test
    fun oneLongSessionConnection() {
        test(Array(3000, {when(it) {
            0 -> Settings(arrayOf("1", "2", "3"))
            in 1..999 -> Click(it % 3)
            1000 -> Settings(arrayOf("q", "w", "e", "r", "t", "y"))
            in 1001..1999 -> Click(it % 2 + it % 3)
            2000 -> Settings(arrayOf("hello"))
            in 2001..2998 -> Click(0)
            else -> Close()
        }}))
    }
}