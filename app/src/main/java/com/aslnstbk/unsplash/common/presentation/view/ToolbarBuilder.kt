package com.aslnstbk.unsplash.common.presentation.view

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.utils.extensions.show

class ToolbarBuilder {

    private var title: String = ""
    private var menu: Int = 0
    private var navigationId: Int = 0
    private var navigationIcon: Drawable? = null

    fun setTitle(title: String): ToolbarBuilder {
        this.title = title

        return this
    }

    fun setMenu(menu: Int): ToolbarBuilder {
        this.menu = menu

        return this
    }

    fun setNavigationIcon(navigationIcon: Drawable?): ToolbarBuilder {
        this.navigationId = 1
        this.navigationIcon = navigationIcon

        return this
    }

    fun build(activity: Activity): Toolbar {
        val toolbar: Toolbar = activity.findViewById(R.id.activity_main_toolbar)

        toolbar.show()
        toolbar.title = this.title
        toolbar.menu.clear()

        if (navigationId != 0) {
            toolbar.navigationIcon = this.navigationIcon
        }

        if (menu != 0) {
            toolbar.inflateMenu(this.menu)
        }

        return toolbar
    }
}