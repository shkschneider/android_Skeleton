package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class CubeOutTransformer : ABaseTransformer() {

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        page.pivotX = if (position < 1.toFloat()) page.width.toFloat() else 1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.rotationY = 1.toFloat() * position
    }

}
