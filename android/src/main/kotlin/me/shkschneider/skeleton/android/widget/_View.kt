package me.shkschneider.skeleton.android.widget

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.RelativeLayout
import androidx.core.animation.addListener
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.content.SkeletonReceiver
import me.shkschneider.skeleton.android.util.Metrics
import me.shkschneider.skeleton.kotlin.jvm.tryOr

object ViewHelper {

    const val ANDROIDXML = "http://schemas.android.com/apk/res/android"
    const val RESAUTOXML = "http://schemas.android.com/apk/res-auto"
    const val CONTENT = Window.ID_ANDROID_CONTENT
    const val NO_ID = View.NO_ID
    const val RESULT_VISIBILITY = "VISIBILITY"

    fun randomId() = View.generateViewId()

}

var View.isVisible
    get() = visibility == VISIBLE
    set(value) { visibility = if (value) VISIBLE else INVISIBLE }

var View.isInvisible
    get() = visibility == INVISIBLE
    set(value) { visibility = if (value) INVISIBLE else VISIBLE }

var View.isGone
    get() = visibility == GONE
    set(value) { visibility = if (value) GONE else VISIBLE }

// <https://github.com/nowfalsalahudeen/KdroidExt>
fun View.setSize(width: Int, height: Int) {
    with(layoutParams.also {
        it.width = width
        it.height = height
    }) {
        layoutParams = this
    }
}

fun View.revealOn() {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View,
                                    left: Int, top: Int, right: Int, bottom: Int,
                                    oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            removeOnLayoutChangeListener(this)
            val width = measuredWidth / 2
            val height = measuredHeight / 2
            val radius = width.coerceAtLeast(height) / 2
            ViewAnimationUtils.createCircularReveal(this@revealOn, width, height, 1.toFloat(), radius.toFloat()).run {
                // setInterpolator(new AccelerateDecelerateInterpolator());
                duration = ApplicationHelper.resources.getInteger(R.integer.sk_animation_medium).toLong()
                visibility = VISIBLE
                start()
            }
        }
    })
}

fun View.revealOff() {
    val width = measuredWidth / 2
    val height = measuredHeight / 2
    val radius = width / 2
    ViewAnimationUtils.createCircularReveal(this, width, height, radius.toFloat(), 1.toFloat()).run {
        // setInterpolator(new AccelerateDecelerateInterpolator());
        duration = ApplicationHelper.resources.getInteger(R.integer.sk_animation_medium).toLong()
        addListener {
            visibility = GONE
        }
        start()
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

private val androidConfigMediumAnimTime: Long = tryOr(400L) {
    Resources.getSystem().getInteger(android.R.integer.config_mediumAnimTime).toLong()
} as Long

fun View.fadeIn(durationMillis: Long = androidConfigMediumAnimTime) {
    clearAnimation()
    startAnimation(AlphaAnimation(alpha, 1.0F).apply {
        duration = durationMillis
        interpolator = AccelerateInterpolator()
    })
}

fun View.fadeOut(durationMillis: Long = androidConfigMediumAnimTime) {
    clearAnimation()
    startAnimation(AlphaAnimation(alpha, 0.0F).apply {
        duration = durationMillis
        interpolator = AccelerateInterpolator()
    })
}

val View.backgroundColor: Int? get() = (background as? ColorDrawable)?.color
