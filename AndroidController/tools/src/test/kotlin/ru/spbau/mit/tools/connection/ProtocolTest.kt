package ru.spbau.mit.tools.connection

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.test.assertEquals
import kotlin.test.assertTrue


sealed class Command
data class Settings(val arr: Array<String>) : Command()
data class Click(val key: Int) : Command()
class Close : Command()

class TestHandler(val cmds: Array<Command>) : Handler {
    private var index = 0
    fun check() {
        assertEquals(index, cmds.size)
    }

    override fun onSetting(arr: Array<String>) {
        val cur = cmds[index] as Settings
        assertArrayEquals(arr, cur.arr)
        index++
    }

    override fun onClick(cmd: Int) {
        val cur = cmds[index] as Click
        assertEquals(cmd, cur.key)
        index++
    }

    override fun onClose() {
        cmds[index] as Close
        index++
    }
}

class TestHandlerBuilder(val cmds: Array<Array<Command>>) : () -> Handler {
    var index = 0
    val handlerKeeper = ArrayList<TestHandler>()
    override fun invoke(): Handler {
        val handler = TestHandler(cmds[index])
        handlerKeeper.add(handler)
        index++
        return handler
    }

    fun check() {
        assertEquals(handlerKeeper.size, cmds.size)
        handlerKeeper.forEach { it.check() }
    }
}

class ProtocolTest {
    fun test(cmds: Array<Array<Command>>) {
        val handler = TestHandlerBuilder(cmds)
        val server = IPServer(handler)
        server.start()

        val connections = Array(cmds.size, {
            val client = SocketConnection()
            assertTrue(client.connect("localhost" + ':' + server.getPort()))
            client
        })
        val threads = Array(cmds.size, { index ->
            thread {
                val client = connections[index]
                cmds[index].forEach {
                    when (it) {
                        is Settings -> client.sendSettings(it.arr)
                        is Click -> client.sendCommand(it.key)
                        is Close -> client.close()
                    }
                }
            }
        })
        threads.forEach { it.join() }
        Thread.sleep(1000)

        handler.check()
    }

    @Test
    fun simpleConnectionTest() {
        test(arrayOf(arrayOf(Close() as Command)))
    }

    @Test
    fun oneSessionConnection() {
        test(arrayOf(arrayOf(
                Settings(arrayOf("a", "b", "c")),
                Click(0),
                Click(1),
                Click(0),
                Click(1),
                Click(2),
                Close())))
    }

    @Test
    fun oneLongSessionConnection() {
        test(arrayOf(Array(3000, {
            when (it) {
                0 -> Settings(arrayOf("1", "2", "3"))
                in 1..999 -> Click(it % 3)
                1000 -> Settings(arrayOf("q", "w", "e", "r", "t", "y"))
                in 1001..1999 -> Click(it % 2 + it % 3)
                2000 -> Settings(arrayOf("hello"))
                in 2001..2998 -> Click(0)
                else -> Close()
            }
        })))
    }

    @Test
    fun multithreadLongSessionConnection() {
        test(Array(100, {
            when (it) {
                0 -> arrayOf(Close())
                in 1..50 -> Array(it + 1, { jt ->
                    when (jt) {
                        0 -> Settings(arrayOf(it.toString(), (it * 2).toString()))
                        it -> Close()
                        else -> Click(jt % 2)
                    }
                })
                else -> Array(it + 1, { jt ->
                    when (jt) {
                        it -> Close()
                        0 -> Settings(arrayOf(it.toString(), (it * 2).toString()))
                        in 1..49 -> Click((it + jt) % 2)
                        50 -> Settings(arrayOf(it.toString(), (it * 2).toString(), (it * 3).toString()))
                        else -> Click((it + jt) % 3)
                    }
                })
            }
        }) as Array<Array<Command>>)
    }
}