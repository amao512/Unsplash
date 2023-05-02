package com.aslnstbk.unsplash.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context?.hideKeyboard(view: View?) {
    try {
        val keyboard = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showKeyboard(view: View?) {
    view?.requestFocus()
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let {
        inputMethodManager.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
    }
}
