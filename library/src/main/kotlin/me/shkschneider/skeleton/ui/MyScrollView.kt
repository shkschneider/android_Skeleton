package me.shkschneider.skeleton.ui

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView

import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.LogHelper

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/>
// <https://stackoverflow.com/a/26990539/603270>
// MyScrollView.setOnScrollViewListener()
class MyScrollView : ScrollView {

    private var onScrollViewListener: OnScrollViewListener? = null
    private var view: View? = null
    private var parallax: Float? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(AndroidHelper.API_21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setOnScrollViewListener(onScrollViewListener: OnScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        view?.let {
            it.translationY = scrollY * parallax!!
        }
        if (onScrollViewListener != null) {
            onScrollViewListener!!.onScrollChanged(l, t, oldl, oldt)
        }
    }

    fun parallax(view: View) {
        this.view = view
        parallax = PARALLAX
    }

    fun parallax(view: View, parallax: Float) {
        this.view = view
        this.parallax = parallax
        if (parallax <= 0) {
            LogHelper.warning("Parallax effect too low")
            this.parallax = PARALLAX
        }
        if (parallax > 1) {
            LogHelper.warning("Parallax effect too high")
            this.parallax = PARALLAX
        }
    }

    interface OnScrollViewListener {

        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)

    }

    companion object {

        val PARALLAX = 0.1.toFloat()

        fun canScroll(scrollView: ScrollView): Boolean {
            val childHeight = scrollView.height
            return scrollView.height < childHeight + scrollView.paddingTop + scrollView.paddingBottom
        }

    }

}
