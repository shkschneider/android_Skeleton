package me.shkschneider.skeleton.ui.transforms

import android.view.View

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class DefaultTransformer : ABaseTransformer() {

    override fun isPagingEnabled(): Boolean {
        return true
    }

    override fun onTransform(page: View, position: Float) {
        // Empty
    }

}
