package ru.spbau.mit.androidcontroller

import android.app.Application
import ru.spbau.mit.tools.connection.AppConnection
import ru.spbau.mit.tools.connection.SocketConnection

class MyApplication : Application() {
    var connection: AppConnection = SocketConnection()
}