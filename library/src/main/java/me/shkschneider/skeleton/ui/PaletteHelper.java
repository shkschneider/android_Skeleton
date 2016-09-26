package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

import me.shkschneider.skeleton.R;

public class PaletteHelper {

    @ColorRes
    private static final int DEFAULT_COLOR = R.color.sk_android_transparent;

    private static Palette get(@NonNull final Bitmap bitmap) {
        return new Palette.Builder(bitmap).generate();
    }

    public static int vibrantColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getVibrantColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

    public static int lightVibrantColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getLightVibrantColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

    public static int darkVibrantColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkVibrantColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

    public static int mutedColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getMutedColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

    public static int lightMutedColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getLightMutedColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

    public static int darkMutedColor(@NonNull final Context context, @NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkMutedColor(ContextCompat.getColor(context, DEFAULT_COLOR));
    }

}
