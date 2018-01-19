package ru.spbau.mit.tools

import java.awt.Robot

class WordParser : Parser {
    private val robot = Robot()

    override fun parce(s: String): () -> Unit {
        return {println(s)
                robot.keyPress(s[0].toInt())
                robot.keyRelease(s[0].toInt())}
    }
}