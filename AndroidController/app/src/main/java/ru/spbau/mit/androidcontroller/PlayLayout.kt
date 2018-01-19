package ru.spbau.mit.androidcontroller

class PlayLayout(var name: String, val commands: Array<String>) {

    fun getCommand(id: Int): String? {
        return commands[id]
    }

    fun setCommand(id: Int, command: String): Boolean {
        if (id < commands.size && id >= 0) {
            commands[id] = command
            return true
        }
        return false
    }
}