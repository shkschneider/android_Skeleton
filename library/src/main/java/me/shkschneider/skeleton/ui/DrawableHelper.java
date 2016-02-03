package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class DrawableHelper {

    public static Drawable fromResource(@DrawableRes final int id) {
        return ContextCompat.getDrawable(ApplicationHelper.context(), getDrawable(id));
    }

    public static Drawable fromBitmap(@NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), bitmap);
    }

    public static Drawable circular(@NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), BitmapHelper.circular(bitmap));
    }

    public static String toBase64(@NonNull final Drawable drawable) {
        return BitmapHelper.toBase64(BitmapHelper.fromDrawable(drawable));
    }

}
