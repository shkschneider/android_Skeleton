package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class StackTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        page.translationX = if (position < 0) 0f else -page.width * position
    }

}
