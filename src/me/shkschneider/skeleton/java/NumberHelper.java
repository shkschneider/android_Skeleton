package me.shkschneider.skeleton.java;

import java.util.Random;

@SuppressWarnings("unused")
public final class NumberHelper {

    public static int random(final int min, final int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static int random() {
        return new Random().nextInt();
    }

    public static boolean zero(final int integer) {
        return (integer == 0);
    }

}
