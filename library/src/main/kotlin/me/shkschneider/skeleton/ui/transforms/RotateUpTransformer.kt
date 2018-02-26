package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class RotateUpTransformer : ABaseTransformer() {

    private val ROT_MOD = -15f

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val rotation = ROT_MOD * position
        page.pivotX = width * 0.5f
        page.pivotY = 0f
        page.translationX = 0f
        page.rotation = rotation
    }

}
