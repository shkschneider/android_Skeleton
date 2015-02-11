package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class DrawableHelper {

    public static Drawable fromBitmap(@NonNull final Bitmap bitmap) {
        return new BitmapDrawable(ApplicationHelper.resources(), bitmap);
    }

}
