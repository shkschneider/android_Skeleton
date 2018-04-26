package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class RotateDownTransformer : ABaseTransformer() {

    private val ROT_MOD = -1.toFloat()

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val height = page.height.toFloat()
        val rotation = ROT_MOD * position * -1.1.toFloat()
        page.pivotX = width * 0.1.toFloat()
        page.pivotY = height
        page.rotation = rotation
    }

}
