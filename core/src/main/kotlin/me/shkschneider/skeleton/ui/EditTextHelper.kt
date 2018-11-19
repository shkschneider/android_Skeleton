package me.shkschneider.skeleton.ui

import android.annotation.SuppressLint
import android.text.InputFilter
import android.view.MotionEvent
import android.widget.EditText

object EditTextHelper {

    fun maxLength(editText: EditText, maxLength: Int) {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    @SuppressLint("ClickableViewAccessibility")
    // <http://stackoverflow.com/a/20520755>
    fun scrollCompat(editText: EditText, editTextId: Int) {
        editText.setOnTouchListener { v, event ->
            if (v.id == editTextId) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }

    fun autoSize(editText: EditText, autoSize: Boolean) {
        TextViewHelper.autoSize(editText, autoSize)
    }

}
