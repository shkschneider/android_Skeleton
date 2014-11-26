package me.shkschneider.skeleton.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.AndroidHelper;

public class SwitchCompatPreference extends SwitchPreference {

    public SwitchCompatPreference(final Context context) {
        this(context, null);
    }

    public SwitchCompatPreference(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchCompatPreference(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    public SwitchCompatPreference(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R.layout.switch_compat);
    }

}
