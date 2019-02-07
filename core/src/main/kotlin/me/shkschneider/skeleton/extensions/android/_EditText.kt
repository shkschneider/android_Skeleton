package me.shkschneider.skeleton.extensions.android

import android.text.InputFilter
import android.view.MotionEvent
import android.widget.EditText

fun EditText.maxLength(maxLength: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
}

// <http://stackoverflow.com/a/20520755>
fun EditText.scrollCompat(editTextId: Int) {
    setOnTouchListener { v, event ->
        if (v.id == editTextId) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        false
    }
}
