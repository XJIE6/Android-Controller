package ru.spbau.mit.tools

interface Handler {
    fun onSetting(arr : Array<String>)
    fun onClick(cmd : Int)
    fun onClose()
}