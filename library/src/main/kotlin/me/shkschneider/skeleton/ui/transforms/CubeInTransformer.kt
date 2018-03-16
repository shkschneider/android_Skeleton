package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class CubeInTransformer : ABaseTransformer() {

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position > 0) 0 else page.width).toFloat()
        page.pivotY = 1.toFloat()
        page.rotationY = -1.toFloat() * position
    }

}
