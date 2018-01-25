package ru.spbau.mit.androidcontroller

import ru.spbau.mit.tools.connection.AppConnection

class ConnectionMock : AppConnection {
    override fun connect(params: String) {
        return
    }

    override fun sendSettings(settingList: Array<String>) {
        return
    }

    override fun sendCommand(command: Int) {
        return
    }

    override fun close() {
        return
    }

}