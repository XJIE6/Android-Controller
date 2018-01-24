package ru.spbau.mit.androidcontroller

import android.graphics.Color.WHITE
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.sdk25.coroutines.onItemClick

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MenuActivityUI().setContentView(this)
    }
}

class MenuActivityUI : AnkoComponent<MenuActivity> {
    override fun createView(ui: AnkoContext<MenuActivity>) = with(ui) {
        verticalLayout {
            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar {
                    setTitleTextColor(WHITE)
                    title = resources.getString(R.string.app_name)
                    popupTheme = R.style.AppTheme_PopupOverlay
                    lparams(width = matchParent)
                }
            }
            relativeLayout {
                val playAdapter = MenuAdapter(ui.owner)
                listView {
                    adapter = playAdapter
                    isStackFromBottom = false
                    onItemClick { _, _, i, _ ->
                        ui.owner.startActivity(intentFor<SettingsActivity>
                        (resources.getString(R.string.play_layout) to i))
                    }
                }
                floatingActionButton {
                    imageResource = android.R.drawable.ic_input_add
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    margin = dip(16)
                    alignParentBottom()
                    alignParentRight()
                }.setOnClickListener { toast("Push!") }
            }
        }
    }
}
