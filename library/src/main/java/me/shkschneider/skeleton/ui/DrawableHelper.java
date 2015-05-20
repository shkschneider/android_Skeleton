package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class DrawableHelper {

    public static Drawable getDrawable(final int id) {
        return ApplicationHelper.resources().getDrawable(id, null);
    }

    public static Drawable fromBitmap(@NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), bitmap);
    }

    public static Drawable circular(@NonNull final Bitmap bitmap) {
        final RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplicationHelper.resources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(Math.min(roundedBitmapDrawable.getMinimumWidth(), roundedBitmapDrawable.getMinimumHeight()) / 2.0F);
        return roundedBitmapDrawable;
    }

}
