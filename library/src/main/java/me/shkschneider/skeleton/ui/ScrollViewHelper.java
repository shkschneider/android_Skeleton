package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewHelper {

    protected ScrollViewHelper() {
        // Empty
    }

    public static void top(@NonNull final ScrollView scrollView) {
        scrollView.fullScroll(View.FOCUS_UP);
    }

    public static void bottom(@NonNull final ScrollView scrollView) {
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public static void pageUp(@NonNull final ScrollView scrollView) {
        scrollView.pageScroll(View.FOCUS_UP);
    }

    public static void pageDown(@NonNull final ScrollView scrollView) {
        scrollView.pageScroll(View.FOCUS_DOWN);
    }

}
