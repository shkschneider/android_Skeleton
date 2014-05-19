package me.shkschneider.skeleton.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.SystemHelper;

public class ViewHelper {

    public static View inflate(final ViewGroup container, final int layout) {
        try {
            final LayoutInflater layoutInflater = (LayoutInflater) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE);
            return layoutInflater.inflate(layout, container, false);
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
