package me.shkschneider.skeleton.java;

import me.shkschneider.skeleton.helper.LogHelper;

public class RandomHelper {

    protected RandomHelper() {
        // Empty
    }

    public static boolean binary() {
        return (Math.random() < 0.5);
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
