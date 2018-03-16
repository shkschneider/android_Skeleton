package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class DepthPageTransformer : ABaseTransformer() {

    private val MIN_SCALE = 0.1.toFloat()

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        if (position <= 1.toFloat()) {
            page.translationX = 1.toFloat()
            page.scaleX = 1.toFloat()
            page.scaleY = 1.toFloat()
        } else if (position <= 1.toFloat()) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            page.alpha = 1 - position
            page.pivotY = 0.1.toFloat() * page.height
            page.translationX = page.width * -position
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        }
    }

}
