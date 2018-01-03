package me.shkschneider.skeleton.ui.transforms

import android.support.v4.view.ViewPager.PageTransformer
import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
abstract class ABaseTransformer : PageTransformer {

    protected abstract fun onTransform(page: View, position: Float)

    open fun isPagingEnabled(): Boolean {
        return false
    }

    override fun transformPage(page: View, position: Float) {
        onPreTransform(page, position)
        onTransform(page, position)
        onPostTransform(page, position)
    }

    private fun hideOffscreenPages(): Boolean {
        return true
    }

    private fun onPreTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        page.rotationX = 0f
        page.rotationY = 0f
        page.rotation = 0f
        page.scaleX = 1f
        page.scaleY = 1f
        page.pivotX = 0f
        page.pivotY = 0f
        page.translationY = 0f
        page.translationX = if (isPagingEnabled()) 0f else -width * position
        if (hideOffscreenPages()) {
            page.alpha = if (position <= -1f || position >= 1f) 0f else 1f
        } else {
            page.alpha = 1f
        }
    }

    private fun onPostTransform(page: View, position: Float) {
        // Empty
    }

    protected fun min(value: Float, min: Float): Float {
        return if (value < min) min else value
    }

}
