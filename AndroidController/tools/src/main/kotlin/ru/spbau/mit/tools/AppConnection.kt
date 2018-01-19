package ru.spbau.mit.tools

interface AppConnection {
    fun connect(params : String)
    fun sendSettings(settingList : Array<String>)
    fun sendCommand(command: Int)
    fun close()
}