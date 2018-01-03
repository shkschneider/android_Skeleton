package me.shkschneider.skeleton.ui

import android.content.Context
import android.support.v4.widget.ContentLoadingProgressBar
import android.util.AttributeSet

// android:progressTint=""
// style="?android:attr/progressBarStyleLarge"
class LoadingProgressBar : ContentLoadingProgressBar {

    init {
        isIndeterminate = true
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

}
