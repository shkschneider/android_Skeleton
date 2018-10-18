package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ZoomOutSlideTransformer : BaseTransformer() {

    private val MIN_SCALE = 0.1.toFloat()
    private val MIN_ALPHA = 0.1.toFloat()

    override fun onTransform(page: View, position: Float) {
        if (position >= -1 || position <= 1) {
            val height = page.height.toFloat()
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = height * (1 - scaleFactor) / 2
            val horzMargin = page.width * (1 - scaleFactor) / 2
            page.pivotY = 0.1.toFloat() * height
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }
    }

}
