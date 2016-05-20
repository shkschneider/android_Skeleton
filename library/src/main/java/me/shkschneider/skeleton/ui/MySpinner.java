package me.shkschneider.skeleton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

import me.shkschneider.skeleton.helper.AndroidHelper;

// <http://stackoverflow.com/a/11227847>
public class MySpinner extends Spinner {

    private OnItemSelectedListener mOnItemSelectedListener;

    public MySpinner(final Context context) {
        super(context);
    }

    public MySpinner(final Context context, final int mode) {
        super(context, mode);
    }

    public MySpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpinner(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySpinner(final Context context, final AttributeSet attrs, final int defStyleAttr, final int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    @TargetApi(AndroidHelper.API_21)
    public MySpinner(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes, final int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
    }

    @TargetApi(AndroidHelper.API_23)
    public MySpinner(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes, final int mode, final Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }

    @Override
    public void setSelection(final int position) {
        super.setSelection(position);
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(null, null, position, 0);
        }
    }

    @Override
    public void setOnItemSelectedListener(final OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

}
