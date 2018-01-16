package ru.spbau.mit.tools

interface Parser {
    fun parce(s : String) : () -> Unit
}