package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class ZoomInTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = if (position < 0) position + 1f else Math.abs(1f - position)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f
        page.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
    }

}
