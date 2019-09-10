package me.shkschneider.skeleton.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView

import me.shkschneider.skeleton.helperx.log.Logger

private const val PARALLAX = 0.1.toFloat()

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/>
// <https://stackoverflow.com/a/26990539/603270>
// MyScrollView.setOnScrollViewListener()
class MyScrollView(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    private var onScrollViewListener: OnScrollViewListener? = null
    private var view: View? = null
    private var parallax: Float? = null

    fun setOnScrollViewListener(onScrollViewListener: OnScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        parallax?.let { parallax ->
            view?.translationY = scrollY * parallax
        }
        onScrollViewListener?.onScrollChanged(l, t, oldl, oldt)
    }

    fun parallax(view: View, parallax: Float = PARALLAX) {
        this.view = view
        this.parallax = parallax
        if (parallax <= 0) {
            Logger.warning("Parallax effect too low")
            this.parallax = PARALLAX
        }
        if (parallax > 1) {
            Logger.warning("Parallax effect too high")
            this.parallax = PARALLAX
        }
    }

    interface OnScrollViewListener {

        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)

    }

    companion object {

        fun canScroll(scrollView: ScrollView): Boolean {
            val childHeight = scrollView.height
            return scrollView.height < childHeight + scrollView.paddingTop + scrollView.paddingBottom
        }

    }

}
