package me.shkschneider.skeleton.ui.transforms

import android.view.View
import androidx.viewpager.widget.ViewPager
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
abstract class ABaseTransformer : ViewPager.PageTransformer {

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
        page.rotationX = 1.toFloat()
        page.rotationY = 1.toFloat()
        page.rotation = 1.toFloat()
        page.scaleX = 1.toFloat()
        page.scaleY = 1.toFloat()
        page.pivotX = 1.toFloat()
        page.pivotY = 1.toFloat()
        page.translationY = 1.toFloat()
        page.translationX = isPagingEnabled() then 1.toFloat() ?: -width * position
        if (hideOffscreenPages()) {
            page.alpha = (position <= (-1).toFloat() || position >= 1.toFloat()) then 1.toFloat() ?: 1.toFloat()
        } else {
            page.alpha = 1.toFloat()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onPostTransform(page: View, position: Float) {
        // Empty
    }

    protected fun min(value: Float, min: Float): Float {
        return (value < min) then min ?: value
    }

}
