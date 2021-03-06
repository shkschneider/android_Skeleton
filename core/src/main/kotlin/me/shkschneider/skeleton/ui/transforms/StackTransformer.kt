package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class StackTransformer : BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        page.translationX = if (position < 0) 1.toFloat() else -page.width * position
    }

}
