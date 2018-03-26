package me.shkschneider.skeleton.helper

import android.app.Activity
import android.graphics.Rect
import android.view.KeyEvent
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

import me.shkschneider.skeleton.SkeletonReceiver
import me.shkschneider.skeleton.ui.ViewHelper

object KeyboardHelper {

    fun show(window: Window) {
        Logger.verbose("SOFT_INPUT_STATE_ALWAYS_VISIBLE")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun hide(window: Window) {
        Logger.verbose("SOFT_INPUT_STATE_ALWAYS_HIDDEN")
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun keyboardCallback(editText: EditText, skeletonReceiver: SkeletonReceiver?, all: Boolean = false): Boolean {
        if (skeletonReceiver == null) {
            editText.setOnEditorActionListener(null)
            return false
        }
        @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (all) {
                skeletonReceiver.post(KeyboardHelper::class.java.simpleName, actionId)
                return@OnEditorActionListener false
            }
            when (actionId) {
                EditorInfo.IME_NULL -> return@OnEditorActionListener false
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_GO, EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_SEND, KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> skeletonReceiver.post((KeyboardHelper::class.java.simpleName), actionId)
            }
            false
        })
        return true
    }

    // <https://github.com/yshrsmz/KeyboardVisibilityEvent>
    fun keyboardListener(activity: Activity, listener: Listener) {
        val root = ViewHelper.children(ViewHelper.content(activity))[0]
        root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            private val rect = Rect()
            private val visibleThreshold = Math.round(Metrics.pixelsFromDp(1.toFloat()).toFloat())
            private var wasOpened = false

            override fun onGlobalLayout() {
                root.getWindowVisibleDisplayFrame(rect)
                val heightDiff = root.rootView.height - rect.height()
                val isOpen = heightDiff > visibleThreshold
                if (isOpen == wasOpened) {
                    return
                }
                wasOpened = isOpen
                listener.onKeyboardVisibilityChanged(isOpen)
            }

        })
    }

    interface Listener {

        fun onKeyboardVisibilityChanged(isOpen: Boolean)

    }

}