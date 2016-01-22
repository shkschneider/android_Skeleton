package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.shkschneider.skeleton.helper.AndroidHelper;

public class LongPreference extends Preference {

    public LongPreference(final Context context) {
        super(context);
    }

    public LongPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LongPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    @TargetApi(AndroidHelper.API_21)
    public LongPreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onBindView(final View view) {
        super.onBindView(view);
        ((TextView) view.findViewById(android.R.id.summary)).setMaxLines(5);
    }

}
