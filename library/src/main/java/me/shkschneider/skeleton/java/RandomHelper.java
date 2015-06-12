package me.shkschneider.skeleton.java;

public class RandomHelper {

    protected RandomHelper() {
        // Empty
    }

    public static boolean binary() {
        return (Math.random() < 0.5);
    }

    public static int inclusive(final int min, final int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static int exclusive(final int min, final int max) {
        return inclusive(min + 1, max - 1);
    }

}
