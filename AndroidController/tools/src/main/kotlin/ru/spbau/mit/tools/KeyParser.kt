package ru.spbau.mit.tools

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.parser.Parser
import java.awt.Robot

sealed class Cmd
data class Key(val id: Int) : Cmd()
data class Press(val id: Int) : Cmd()
data class Release(val id: Int) : Cmd()
data class Repeat(val count: Int, val cmds : List<Cmd>) : Cmd()

class KeyParser : ru.spbau.mit.tools.Parser {
    private val robot = Robot()

    val grammar = object : Grammar<Cmd>() {
        private val key by token("k")
        private val press by token("p")
        private val release by token("r")
        private val number by token("\\d+")
        private val space by token("\\s+", ignore = true)
        private val lbracket by token("\\[")
        private val rbracket by token("]")
        override val rootParser: Parser<Cmd>  by
            (-key * number use {Key(text.toInt())}) or
            (-press * number use {Press(text.toInt())}) or
            (-release * number use {Release(text.toInt())}) or
            (-lbracket * oneOrMore(parser(this::rootParser)) * -rbracket * number map
                    {(list, count) -> Repeat(count.text.toInt(), list)})

    }
    private fun eval(cmd : Cmd): Unit = when(cmd) {
        is Key -> { robot.keyPress(cmd.id)
                    robot.keyRelease(cmd.id)}
        is Press -> robot.keyPress(cmd.id)
        is Release -> robot.keyRelease(cmd.id)
        is Repeat -> repeat(cmd.count, {_ -> cmd.cmds.forEach({it -> eval(it)})})
    }
    override fun parce(s: String): () -> Unit {
        val res = grammar.parseToEnd(s)
        return{eval(res)}
    }
}