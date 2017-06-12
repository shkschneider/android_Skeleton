package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ContextHelper;

public class PaletteHelper {

    @ColorRes
    private static final int DEFAULT_COLOR = R.color.sk_android_transparent;

    private static Palette get(@NonNull final Bitmap bitmap) {
        return new Palette.Builder(bitmap).generate();
    }

    public static int vibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

    public static int lightVibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getLightVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

    public static int darkVibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkVibrantColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

    public static int mutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

    public static int lightMutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getLightMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

    public static int darkMutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkMutedColor(ContextCompat.getColor(ContextHelper.applicationContext(), DEFAULT_COLOR));
    }

}
