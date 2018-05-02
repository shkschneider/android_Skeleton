package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class StackTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        page.translationX = (position < 0) then 1.toFloat() ?: -page.width * position
    }

}
