package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class CubeInTransformer : ABaseTransformer() {

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        page.pivotX = ((position > 0) then 0 ?: page.width).toFloat()
        page.pivotY = 1.toFloat()
        page.rotationY = (-1).toFloat() * position
    }

}
