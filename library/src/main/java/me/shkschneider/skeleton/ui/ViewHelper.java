package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.java.SkHide;

public class ViewHelper {

    protected ViewHelper() {
        // Empty
    }

    @SkHide
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    @SkHide
    final static String RESAUTOXML = "http://schemas.android.com/apk/res-auto";

    public static View content(@NonNull final Activity activity) {
        return activity.findViewById(Window.ID_ANDROID_CONTENT);
    }

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
