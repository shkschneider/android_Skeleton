package me.shkschneider.skeleton.security;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.MessageDigest;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class HashHelper {

    protected HashHelper() {
        // Empty
    }

    protected static final String MD5 = "MD5";
    protected static final String SHA = "SHA";

    @Nullable
    private static String hash(@NonNull final String algorithm, @NonNull final String string) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.setLength(0);
            final byte digest[] = messageDigest.digest();
            for (final byte b : digest) {
                stringBuilder.append(Integer.toString((b & 0xFF) + 0x100, StringHelper.HEX.length()).substring(1));
            }
            return stringBuilder.toString();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String md5(@NonNull final String string) {
        return hash(MD5, string);
    }

    public static String sha(@NonNull final String string) {
        return hash(SHA, string);
    }

}
