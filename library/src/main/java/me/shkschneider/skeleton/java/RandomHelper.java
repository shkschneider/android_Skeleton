package me.shkschneider.skeleton.java;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Random;

import me.shkschneider.skeleton.helper.LogHelper;

public class RandomHelper {

    protected RandomHelper() {
        // Empty
    }

    public static boolean binary() {
        return (Math.random() < 0.5);
    }

    public static String string(@NonNull final String characters, @IntRange(from=0) final int length) {
        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    public static String string(@IntRange(from=0) final int length) {
        return string(StringHelper.lower(StringHelper.HEX), length);
    }

    public static int inclusive(final int min, final int max) {
        if (min > max) {
            LogHelper.warning("MIN was greater than MAX");
            return -1;
        }
        if (min == max) {
            LogHelper.info("MIN was equal to MAX");
            return min;
        }
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static int exclusive(final int min, final int max) {
        return inclusive(min + 1, max - 1);
    }

}
