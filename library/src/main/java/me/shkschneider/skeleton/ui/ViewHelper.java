package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {

    protected ViewHelper() {
        // Empty
    }

    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    final static String RESAUTOXML = "http://schemas.android.com/apk/res-auto";

    public static List<View> children(@NonNull final View view) {
        final List<View> views = new ArrayList<>();
        final ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            final View v = viewGroup.getChildAt(i);
            views.add(v);
        }
        return views;
    }

}
