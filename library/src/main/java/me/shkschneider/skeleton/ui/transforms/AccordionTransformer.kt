package me.shkschneider.skeleton.ui.transforms

import android.view.View
import me.shkschneider.skeleton.extensions.then

// <https://github.com/ToxicBakery/ViewPagerTransforms>
class AccordionTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        page.pivotX = ((position < 0) then 0 ?: page.width).toFloat()
        page.scaleX = (position < 0) then 1.toFloat() + position ?: 1.toFloat() - position
    }

}
