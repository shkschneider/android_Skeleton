package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class LongPreference extends Preference {

    public LongPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LongPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LongPreference(final Context context) {
        super(context);
    }

    @Override
    protected void onBindView(final View view) {
        super.onBindView(view);
        ((TextView) view.findViewById(android.R.id.summary)).setMaxLines(5);
    }

}
