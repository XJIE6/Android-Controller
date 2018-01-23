package ru.spbau.mit.tools.lang

sealed class Cmd
data class Key(val id: Int) : Cmd()
data class Press(val id: Int) : Cmd()
data class Release(val id: Int) : Cmd()
data class Repeat(val count: Int, val cmds : List<Cmd>) : Cmd()