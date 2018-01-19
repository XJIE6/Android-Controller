package ru.spbau.mit.tools

import java.awt.Robot
import java.awt.event.KeyEvent.*

class WordParser : Parser {
    private val robot = Robot()

    override fun parce(s: String): () -> Unit {
        return {s.split('+').map {
            when (it) {
                "\\right" -> VK_RIGHT
                "\\left"  -> VK_LEFT
                "\\up"    -> VK_UP
                "\\down"  -> VK_DOWN
                else -> if (it[0] >= 'a' && it[0] <= 'z') {
                            it[0].toInt() - 'a'.toInt() + 'A'.toInt()
                        }
                        else {
                            it[0].toInt()
                        }
            }}.forEach { robot.keyPress(it)
                         robot.keyRelease(it)}}
    }
}