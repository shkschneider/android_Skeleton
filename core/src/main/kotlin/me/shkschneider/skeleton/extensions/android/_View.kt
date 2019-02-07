package me.shkschneider.skeleton.extensions.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.annotation.UiThread
import androidx.core.animation.addListener
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.SkeletonReceiver
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helperx.Metrics

object ViewHelper {

    const val ANDROIDXML = "http://schemas.android.com/apk/res/android"
    const val RESAUTOXML = "http://schemas.android.com/apk/res-auto"
    const val CONTENT = Window.ID_ANDROID_CONTENT
    const val NO_ID = View.NO_ID
    const val RESULT_VISIBILITY = "VISIBILITY"

}

fun View.children(): List<View> {
    val viewGroup = this as ViewGroup
    return (0 until viewGroup.childCount).mapTo(ArrayList<View>()) { viewGroup.getChildAt(it) }
}

@UiThread
fun View.show(): Boolean {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
        return true
    }
    return false
}

fun View.revealOn() {
    if (Build.VERSION.SDK_INT >= 21) {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View,
                                        left: Int, top: Int, right: Int, bottom: Int,
                                        oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                removeOnLayoutChangeListener(this)
                val width = measuredWidth / 2
                val height = measuredHeight / 2
                val radius = Math.max(width, height) / 2
                if (Build.VERSION.SDK_INT >= 21) {
                    ViewAnimationUtils.createCircularReveal(this@revealOn, width, height, 1.toFloat(), radius.toFloat()).run {
                        // setInterpolator(new AccelerateDecelerateInterpolator());
                        duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
                        show()
                        start()
                    }
                }
            }
        })
    } else {
        show()
    }
}

@UiThread
fun View.hide(): Boolean {
    if (visibility != View.GONE) {
        visibility = View.GONE
        return true
    }
    return false
}

fun View.revealOff() {
    if (Build.VERSION.SDK_INT >= 21) {
        val width = measuredWidth / 2
        val height = measuredHeight / 2
        val radius = width / 2
        ViewAnimationUtils.createCircularReveal(this, width, height, radius.toFloat(), 1.toFloat()).run {
            // setInterpolator(new AccelerateDecelerateInterpolator());
            duration = ApplicationHelper.resources().getInteger(R.integer.sk_animation_medium).toLong()
            addListener {
                hide()
            }
            start()
        }
    } else {
        hide()
    }
}

// <https://stackoverflow.com/a/32778292>
fun View.setVisibilityListener(skeletonReceiver: SkeletonReceiver) {
    tag = visibility
    viewTreeObserver.addOnGlobalLayoutListener {
        if (tag != visibility) {
            tag = visibility
            skeletonReceiver.post(ViewHelper.RESULT_VISIBILITY, visibility)
        }
    }
}

fun View.screenshot(): Bitmap? {
    with(Metrics.displayMetrics()) {
        layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        measure(widthPixels, heightPixels)
        layout(0, 0, widthPixels, heightPixels)
    }
    if (Build.VERSION.SDK_INT < 28) {
        /**
         * This method was deprecated in API level 28. The view drawing cache was largely
         * made obsolete with the introduction of hardware-accelerated rendering in API 11.
         */
        @Suppress("DEPRECATION")
        buildDrawingCache()
    }
    return Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888).also { bitmap ->
        draw(Canvas(bitmap))
    }
}