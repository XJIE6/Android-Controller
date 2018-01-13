package ru.spbau.mit.tools

interface AppConnection {
    fun connect(params : String): Boolean
    fun sendSettings(settingList : Array<() -> Unit>)
    fun sendCommand(command: Int)
}