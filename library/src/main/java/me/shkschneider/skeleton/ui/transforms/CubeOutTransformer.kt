package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class CubeOutTransformer : ABaseTransformer() {

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        page.pivotX = (position < 1.toFloat()) then  page.width.toFloat() ?: 1.toFloat()
        page.pivotY = page.height * 0.1.toFloat()
        page.rotationY = 1.toFloat() * position
    }

}
