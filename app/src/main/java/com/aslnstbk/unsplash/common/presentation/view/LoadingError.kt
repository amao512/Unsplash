package com.aslnstbk.unsplash.common.presentation.view

import android.view.View
import android.widget.TextView
import com.aslnstbk.unsplash.R
import com.aslnstbk.unsplash.main.APP_ACTIVITY
import com.aslnstbk.unsplash.utils.extensions.hide
import com.aslnstbk.unsplash.utils.extensions.show

class LoadingError {

    private lateinit var loadingErrorView: View
    private lateinit var fragmentLayout: View
    private lateinit var retryTextButton: TextView

    fun init(){
        loadingErrorView = APP_ACTIVITY.findViewById(R.id.activity_main_loading_error)
        fragmentLayout = APP_ACTIVITY.findViewById(R.id.activity_main_fragment_container)
        retryTextButton = loadingErrorView.findViewById(R.id.layout_loading_error_retry_button)

        hide()
    }

    fun show() {
        fragmentLayout.hide()
        loadingErrorView.show()
    }

    fun hide() {
        loadingErrorView.hide()
        fragmentLayout.show()
    }

    fun onRetryClick(onClick: () -> Unit){
        retryTextButton.setOnClickListener {
            onClick()
            hide()
        }
    }
}