package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;

// android:progressTint=""
// style="?android:attr/progressBarStyleLarge"
public class LoadingProgressBar extends ContentLoadingProgressBar {

    public LoadingProgressBar(final Context context) {
        this(context, null);
    }

    public LoadingProgressBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        setIndeterminate(true);
    }

}
