package ru.spbau.mit.androidcontroller

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onTouch
import org.jetbrains.anko.textView

class MenuAdapter(activity : MenuActivity) : BaseAdapter() {
    var array : Array<String> = ScreenStorage.getNames()

    override fun getView(i : Int, v : View?, parent : ViewGroup?) : View {
        val item = getItem(i)
        return with(parent!!.context) {
            relativeLayout {
                textView(item) {
                    textSize = 32f
                }
            }
        }
    }

    override fun getItem(position : Int) : String {
        return array[position]
    }

    override fun getCount() : Int {
        return array.size
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }
}