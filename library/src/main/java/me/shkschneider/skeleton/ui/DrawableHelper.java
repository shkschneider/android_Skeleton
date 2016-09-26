package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class DrawableHelper {

    public static Drawable fromResource(@NonNull final Context context, @DrawableRes final int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public static Drawable fromBitmap(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(context), bitmap);
    }

    public static Drawable circular(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(context), BitmapHelper.circular(bitmap));
    }

    public static String toBase64(@NonNull final Drawable drawable) {
        return BitmapHelper.toBase64(BitmapHelper.fromDrawable(drawable));
    }

}
