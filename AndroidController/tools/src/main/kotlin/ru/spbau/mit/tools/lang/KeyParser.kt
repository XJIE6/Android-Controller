package ru.spbau.mit.tools.lang

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.parser.Parser

class KeyParser {

    private val grammar = object : Grammar<Cmd>() {
        private val press by token("\\+")
        private val release by token("-")
        private val number by token("\\d+")
        private val space by token("\\s+", ignore = true)
        private val lbracket by token("\\[")
        private val rbracket by token("]")
        private val sugar by token("\\S")
        private val repeat by (-lbracket * oneOrMore(parser(this::command)) * -rbracket * number map
                {(list, count) -> Repeat(count.text.toInt(), list) })
        private val command : Parser<Cmd> by
            (number use { Key(text.toInt()) }) or
            (-press * (
                    (number use { Press(text.toInt()) }) or
                    (sugar use { Press(charToCode(text[0])) })
                    )) or
            (-release * (
                    (number use { Release(text.toInt()) }) or
                    (sugar use { Release(charToCode(text[0])) })
                    )) or
            repeat or
            (sugar use { Key(charToCode(text[0])) })
        override val rootParser by repeat or ((oneOrMore(parser(this::command)) map {Repeat(1, it)}))

    }
    private fun charToCode(symbol : Char) : Int = when(symbol) {
        in 'a'..'z' -> (symbol - ('a' - 'A')).toInt()
        in '0'..'9' -> symbol.toInt()
        'E' -> 10 //Enter
        'P' -> 32 //Space
        'T' -> 9 //Tab
        'S' -> 16 //Shift
        'C' -> 17 //Control
        'A' -> 18 //Alt
        'K' -> 20 //CapsLock
        'L' -> 37 //Left
        'R' -> 39 //Right
        'U' -> 38 //Up
        'D' -> 40 //Down
        'Z' -> 127 //Delete
        'B' -> 8 //Backspace
        'X' -> 27 //Escape
        'W' -> 524 //Windows
        else -> 0
    }
    fun parse(s: String): Cmd {
        return grammar.parseToEnd(s)
    }
}