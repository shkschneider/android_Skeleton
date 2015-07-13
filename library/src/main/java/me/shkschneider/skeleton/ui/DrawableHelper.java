package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class DrawableHelper {

    // TODO randomColor()

    @SuppressWarnings("deprecation")
    public static Drawable fromResource(@DrawableRes final int id) {
        return ApplicationHelper.resources().getDrawable(id);
    }

    public static Drawable fromBitmap(@NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), bitmap);
    }

    public static Drawable circular(@NonNull final Bitmap bitmap) {
        final RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(ApplicationHelper.resources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(Math.min(roundedBitmapDrawable.getMinimumWidth(), roundedBitmapDrawable.getMinimumHeight()) / 2.0F);
        return roundedBitmapDrawable;
    }

    public static String toBase64(@NonNull final Drawable drawable) {
        return BitmapHelper.toBase64(BitmapHelper.fromDrawable(drawable));
    }

}
