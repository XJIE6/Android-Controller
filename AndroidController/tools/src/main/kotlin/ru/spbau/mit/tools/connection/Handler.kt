package ru.spbau.mit.tools.connection

interface Handler {
    fun onSetting(arr: Array<String>)
    fun onClick(cmd: Int)
    fun onClose()
}