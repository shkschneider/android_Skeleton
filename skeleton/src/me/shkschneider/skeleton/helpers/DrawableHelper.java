package me.shkschneider.skeleton.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import me.shkschneider.skeleton.SkeletonApplication;

public class DrawableHelper {

    public static Drawable fromResource(final int id) {
        try {
            return ApplicationHelper.resources().getDrawable(id);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Drawable fromBitmap(final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), bitmap);
    }

    public static Drawable indeterminate() {
        return new ProgressBar(SkeletonApplication.CONTEXT).getIndeterminateDrawable();
    }

}
