package me.shkschneider.skeleton.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class ViewHelper {

    protected ViewHelper() {
        // Empty
    }

    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    final static String RESAUTOXML = "http://schemas.android.com/apk/res-auto";

    public static <T extends View> T content(@NonNull final Activity activity) {
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

    public static boolean show(@NonNull final View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    public static boolean hide(@NonNull final View view) {
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

}
