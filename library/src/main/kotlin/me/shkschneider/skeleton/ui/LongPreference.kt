package me.shkschneider.skeleton.ui

import android.annotation.TargetApi
import android.content.Context
import android.preference.Preference
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import me.shkschneider.skeleton.helper.AndroidHelper

class LongPreference : Preference {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(AndroidHelper.API_21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onBindView(view: View) {
        super.onBindView(view)
        (view.findViewById<View>(android.R.id.summary) as TextView).maxLines = 5
    }

}
