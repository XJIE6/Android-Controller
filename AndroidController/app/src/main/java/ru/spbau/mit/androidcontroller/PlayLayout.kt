package ru.spbau.mit.androidcontroller

class PlayLayout(var name: String, val commands: MutableMap<Int, String>) {

    fun getCommand(id: Int): String? {
        return commands[id]
    }

    fun setCommand(id: Int, command: String): Boolean {
        if (id in commands) {
            commands[id] = command
            return true
        }
        return false
    }
}