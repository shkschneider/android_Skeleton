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

    companion object {

        const val PARALLAX = 0.5f

        fun canScroll(scrollView: ScrollView): Boolean {
            val childHeight = scrollView.height
            return scrollView.height < childHeight + scrollView.paddingTop + scrollView.paddingBottom
        }

    }

    private var mOnScrollViewListener: OnScrollViewListener? = null
    private var mView: View? = null
    private var mParallax: Float? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(AndroidHelper.API_21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setOnScrollViewListener(onScrollViewListener: OnScrollViewListener) {
        mOnScrollViewListener = onScrollViewListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mView != null) {
            mView!!.translationY = scrollY * mParallax!!
        }
        if (mOnScrollViewListener != null) {
            mOnScrollViewListener!!.onScrollChanged(l, t, oldl, oldt)
        }
    }

    fun parallax(view: View) {
        mView = view
        mParallax = PARALLAX
    }

    fun parallax(view: View, parallax: Float) {
        var _parallax = parallax
        if (_parallax <= 0) {
            LogHelper.warning("Parallax effect too low")
            _parallax = PARALLAX
        }
        if (_parallax > 1) {
            LogHelper.warning("Parallax effect too high")
            _parallax = PARALLAX
        }
        mView = view
        mParallax = _parallax
    }

    interface OnScrollViewListener {

        fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)

    }

}
