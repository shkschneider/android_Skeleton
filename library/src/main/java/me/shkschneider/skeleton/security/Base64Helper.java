package me.shkschneider.skeleton.security;

import android.util.Base64;

public class Base64Helper {

    protected Base64Helper() {
        // Empty
    }

    public static final int FLAGS = Base64.NO_WRAP;

    public static String encrypt(final String string) {
        return Base64.encodeToString(string.getBytes(), FLAGS);
    }

    public static String encrypt(final byte[] bytes) {
        return Base64.encodeToString(bytes, FLAGS);
    }

    public static String decrypt(final String string) {
        return new String(Base64.decode(string, FLAGS));
    }

    public static String decrypt(final byte[] bytes) {
        return Base64.encodeToString(bytes, FLAGS);
    }

}
