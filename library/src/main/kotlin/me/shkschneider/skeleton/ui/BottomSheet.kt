package me.shkschneider.skeleton.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.support.annotation.UiThread
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.ScreenHelper

// <https://github.com/javiersantos/BottomDialogs>
class BottomSheet {

    private var dialog: Dialog? = null

    constructor(builder: Builder) {
        dialog = Dialog(builder.activity, R.style.SkeletonTheme_BottomSheet)
        @SuppressLint("InflateParams")
        val view = builder.activity.layoutInflater.inflate(R.layout.sk_bottomsheet, null)
        val icon = view.findViewById<ImageView>(R.id.sk_bottomsheet_icon)
        if (builder.icon != null) {
            icon.visibility = View.VISIBLE
            icon.setImageDrawable(builder.icon)
        }
        val title = view.findViewById<TextView>(R.id.sk_bottomsheet_title)
        if (builder.title != null) {
            title.text = builder.title
        }
        val content = view.findViewById<TextView>(R.id.sk_bottomsheet_content)
        if (builder.content != null) {
            content.text = builder.content
        }
        val customView = view.findViewById<FrameLayout>(R.id.sk_bottomsheet_customView)
        if (builder.customView != null) {
            customView.addView(builder.customView)
            customView.setPadding(builder.paddingLeft, builder.paddingTop, builder.paddingRight, builder.paddingBottom)
        }
        val negative = view.findViewById<Button>(R.id.sk_bottomsheet_cancel)
        if (builder.negative != null) {
            negative.visibility = View.VISIBLE
            negative.text = builder.negative
            negative.setOnClickListener {
                if (builder.negativeCallback != null)
                    builder.negativeCallback!!.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
                dialog?.dismiss()
            }
        }
        val positive = view.findViewById<Button>(R.id.sk_bottomsheet_ok)
        if (builder.positive != null) {
            positive.visibility = View.VISIBLE
            positive.text = builder.positive
            positive.setOnClickListener {
                if (builder.positiveCallback != null)
                    builder.positiveCallback!!.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
                dialog?.dismiss()
            }
        }
        dialog?.setContentView(view)
        dialog?.setCancelable(builder.cancelable)
        val window = dialog?.window
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.BOTTOM)
        }
    }

    @UiThread
    fun show() {
        dialog?.show()
    }

    @UiThread
    fun dismiss() {
        dialog?.dismiss()
    }

    class Builder {

        val activity: Activity
        var icon: Drawable? = null
        var title: String? = null
        var content: String? = null
        var negative: String? = null
        var positive: String? = null
        var negativeCallback: DialogInterface.OnClickListener? = null
        var positiveCallback: DialogInterface.OnClickListener? = null
        var customView: View? = null
        var paddingLeft: Int = 0
        var paddingTop: Int = 0
        var paddingRight: Int = 0
        var paddingBottom: Int = 0
        var cancelable: Boolean = false

        constructor(activity: Activity) {
            this.activity = activity
            this.cancelable = true
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setIcon(icon: Drawable): Builder {
            this.icon = icon
            return this
        }

        fun setPositive(positive: String, onClickListener: DialogInterface.OnClickListener?): Builder {
            this.positive = positive
            this.positiveCallback = onClickListener
            return this
        }

        fun setNegative(negative: String, onClickListener: DialogInterface.OnClickListener?): Builder {
            this.negative = negative
            this.negativeCallback = onClickListener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setCustomView(customView: View): Builder {
            this.customView = customView
            this.paddingLeft = 0
            this.paddingTop = 0
            this.paddingRight = 0
            this.paddingBottom = 0
            return this
        }

        fun setCustomView(customView: View, left: Int, top: Int, right: Int, bottom: Int): Builder {
            this.customView = customView
            this.paddingLeft = ScreenHelper.pixelsFromDp(left.toFloat())
            this.paddingTop = ScreenHelper.pixelsFromDp(top.toFloat())
            this.paddingRight = ScreenHelper.pixelsFromDp(right.toFloat())
            this.paddingBottom = ScreenHelper.pixelsFromDp(bottom.toFloat())
            return this
        }

        @UiThread
        fun build(): BottomSheet {
            return BottomSheet(this)
        }

    }

}
