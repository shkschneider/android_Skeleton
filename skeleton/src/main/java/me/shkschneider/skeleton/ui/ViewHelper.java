package me.shkschneider.skeleton.ui;

import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.SystemServices;

public class ViewHelper {

    public static View inflate(final ViewGroup container, final int layout) {
        try {
            return SystemServices.layoutInflater().inflate(layout, container, false);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static View inflate(final int layout) {
        return inflate(null, layout);
    }

}
