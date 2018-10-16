package me.shkschneider.skeleton.ui

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.UiThread
import me.shkschneider.skeleton.R
import me.shkschneider.skeleton.helper.Metrics

// <https://github.com/javiersantos/BottomDialogs>
// app:layout_behavior="android.support.design.widget.BottomSheetBehavior"
class BottomSheet(val builder: Builder) {

    private var dialog: Dialog

    init {
        dialog = Dialog(builder.activity, R.style.SkeletonTheme_BottomSheet)
        val view = builder.activity.layoutInflater.inflate(R.layout.sk_bottomsheet, null)
        val icon = view.findViewById<ImageView>(R.id.sk_bottomsheet_icon)
        icon.visibility = View.VISIBLE
        icon.setImageDrawable(builder.icon)
        val title = view.findViewById<TextView>(R.id.sk_bottomsheet_title)
        title.text = builder.title
        val content = view.findViewById<TextView>(R.id.sk_bottomsheet_content)
        content.text = builder.content
        val customView = view.findViewById<FrameLayout>(R.id.sk_bottomsheet_customView)
        builder.customView?.let {
            customView.addView(it)
            customView.setPadding(builder.paddingLeft, builder.paddingTop, builder.paddingRight, builder.paddingBottom)
        }
        val negative = view.findViewById<Button>(R.id.sk_bottomsheet_cancel)
        builder.negative?.let { text ->
            negative.visibility = View.VISIBLE
            negative.text = text
            negative.setOnClickListener {
                builder.negativeCallback?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
                dialog.dismiss()
            }
        }
        val positive = view.findViewById<Button>(R.id.sk_bottomsheet_ok)
        builder.positive?.let { text ->
            positive.visibility = View.VISIBLE
            positive.text = text
            positive.setOnClickListener {
                builder.positiveCallback?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
                dialog.dismiss()
            }
        }
        dialog.setContentView(view)
        dialog.setCancelable(builder.cancelable)
        dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    @UiThread
    fun show() {
        dialog.show()
    }

    @UiThread
    fun dismiss() {
        dialog.dismiss()
    }

    class Builder(val activity: Activity) {

        var icon: Drawable? = null
        var title: String? = null
        var content: String? = null
        var negative: String? = null
        var positive: String? = null
        var negativeCallback: DialogInterface.OnClickListener? = null
        var positiveCallback: DialogInterface.OnClickListener? = null
        var customView: View? = null
        var paddingLeft = 0
        var paddingTop = 0
        var paddingRight = 0
        var paddingBottom = 0
        var cancelable = false

        init {
            cancelable = true
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
            positiveCallback = onClickListener
            return this
        }

        fun setNegative(negative: String, onClickListener: DialogInterface.OnClickListener?): Builder {
            this.negative = negative
            negativeCallback = onClickListener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setCustomView(customView: View): Builder {
            this.customView = customView
            paddingLeft = 0
            paddingTop = 0
            paddingRight = 0
            paddingBottom = 0
            return this
        }

        fun setCustomView(customView: View, left: Int, top: Int, right: Int, bottom: Int): Builder {
            this.customView = customView
            paddingLeft = Metrics.pixelsFromDp(left.toFloat())
            paddingTop = Metrics.pixelsFromDp(top.toFloat())
            paddingRight = Metrics.pixelsFromDp(right.toFloat())
            paddingBottom = Metrics.pixelsFromDp(bottom.toFloat())
            return this
        }

        @UiThread
        fun build(): BottomSheet {
            return BottomSheet(this)
        }

    }

}
