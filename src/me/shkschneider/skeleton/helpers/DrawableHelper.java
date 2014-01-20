package me.shkschneider.skeleton.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import me.shkschneider.skeleton.SkeletonApplication;

@SuppressWarnings("unused")
public final class DrawableHelper {

    public static Drawable fromResource(final int id) {
        try {
            return SkeletonApplication.CONTEXT.getResources().getDrawable(id);
        }
        catch (final Resources.NotFoundException e) {
            LogHelper.error("NotFoundException: " + e.getMessage());
            return null;
        }
    }

    public static Drawable fromBitmap(final Bitmap bitmap) {
        return new BitmapDrawable(SkeletonApplication.CONTEXT.getResources(), bitmap);
    }

    public static Drawable indeterminate() {
        return new ProgressBar(SkeletonApplication.CONTEXT).getIndeterminateDrawable();
    }

}
