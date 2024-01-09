package com.amaris.movies.base

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.viewbinding.ViewBinding

fun ViewBinding.hideKeyboardWhenTouchOutside() {
    root.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            root.hideSoftInput()
            v.performClick()
        }
        false
    }
}

fun View.hideSoftInput() {
    val imm = context.getSystemService<InputMethodManager>() ?: return
    val windowToken = windowToken ?: return
    imm.hideSoftInputFromWindow(windowToken, 0)
}