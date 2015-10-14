package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class PaletteHelper {

    private static final int DEFAULT_COLOR = ApplicationHelper.resources().getColor(R.color.sk_android_transparent);

    private static Palette get(@NonNull final Bitmap bitmap) {
        return new Palette.Builder(bitmap).generate();
    }

    public static int vibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getVibrantColor(DEFAULT_COLOR);
    }

    public static int lightVibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getLightVibrantColor(DEFAULT_COLOR);
    }

    public static int darkVibrantColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkVibrantColor(DEFAULT_COLOR);
    }

    public static int mutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getMutedColor(DEFAULT_COLOR);
    }

    public static int lightMutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getLightMutedColor(DEFAULT_COLOR);
    }

    public static int darkMutedColor(@NonNull final Bitmap bitmap) {
        return get(bitmap).getDarkMutedColor(DEFAULT_COLOR);
    }

}
