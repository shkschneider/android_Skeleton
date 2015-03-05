package me.shkschneider.skeleton.ui;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class PaletteHelper {

    private static final int DEFAULT_COLOR = ApplicationHelper.resources().getColor(R.color.sk_android_transparent);

    public static int vibrantColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getVibrantColor(DEFAULT_COLOR);
    }

    public static int lightVibrantColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getLightVibrantColor(DEFAULT_COLOR);
    }

    public static int darkVibrantColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getDarkVibrantColor(DEFAULT_COLOR);
    }

    public static int mutedColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getMutedColor(DEFAULT_COLOR);
    }

    public static int lightMutedColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getLightMutedColor(DEFAULT_COLOR);
    }

    public static int darkMutedColor(@NonNull final Bitmap bitmap) {
        return Palette.generate(bitmap).getDarkMutedColor(DEFAULT_COLOR);
    }

}
