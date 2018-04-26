package me.shkschneider.skeleton.ui

import android.content.Context
import android.preference.Preference
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

class LongPreference : Preference {

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun onBindView(view: View) {
        super.onBindView(view)
        view.findViewById<TextView>(android.R.id.summary).maxLines = 5
    }

}
