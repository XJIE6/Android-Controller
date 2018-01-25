package ru.spbau.mit.androidcontroller

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.toast

class Screen(@SerializedName("name") val name: String, @SerializedName("playLayoutName") private val playLayoutNum: Int,
             @SerializedName("commands") val commands: Array<String> = PlayLayoutStorage.layouts[playLayoutNum].DEFAULT_COMMANDS) {
    private fun getPreviewLambdas(ctx: Context): Array<(View) -> Unit> {
        return Array(commands.size, { num: Int ->
            { view: View ->
                val editChange = EditText(ctx)
                editChange.id = R.id.change_command
                editChange.setText(commands[num])
                AlertDialog
                        .Builder(ctx)
                        .setTitle("Change command")
                        .setMessage("Enter the command of the button ${view.findViewById<Button>(num).text}")
                        .setView(editChange)
                        .setNeutralButton(R.string.ok, { _, _ ->
                            commands.set(num, editChange.text.toString())
                        }).create().show()
            }
        })
    }

    private fun getPlayLambdas(ctx: Context): Array<(View) -> Unit> {
        return Array(commands.size, { num: Int ->
            { view: View ->
//                ctx.toast(commands[num]).show()
                MainActivity.connection.sendCommand(num)
            }
        })
    }

    fun buildScreenPreview(ctx: Context): ViewManager.() -> LinearLayout =
            PlayLayoutStorage.layouts[playLayoutNum].build(ctx, getPreviewLambdas(ctx))
    fun buildScreenPlay(ctx: Context): ViewManager.() -> LinearLayout =
            PlayLayoutStorage.layouts[playLayoutNum].build(ctx, getPlayLambdas(ctx))
}

object ScreenStorage {
    var screens: ArrayList<Screen> = arrayListOf(Screen("Screen1", 0),
            Screen("Screen2", 1), Screen("Screen3", 2))

    fun loadData(ctx: AppCompatActivity) {
        val sharedPref = ctx.getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString(ctx.getString(R.string.saved_data), "")
        if (json != "") {
            screens = gson.fromJson<ArrayList<Screen>>(json, object : TypeToken<List<Screen>>() {}.type)
        }
        screens.sortBy { screen -> screen.name }
    }

    fun saveData(ctx: AppCompatActivity) {
        val gson = Gson()
        val stringScreens: String = gson.toJson(screens)

        val sharedPref = ctx.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(ctx.getString(R.string.saved_data), stringScreens)
            commit()
        }
    }

    fun getNames(): Array<String> {
        return screens.map { screen -> screen.name }.toTypedArray()
    }
}