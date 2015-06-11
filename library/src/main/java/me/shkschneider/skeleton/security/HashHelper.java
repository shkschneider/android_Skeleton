package me.shkschneider.skeleton.security;

import java.security.MessageDigest;

import me.shkschneider.skeleton.helper.Log;
import me.shkschneider.skeleton.java.StringHelper;

public class HashHelper {

    protected static final String MD5 = "MD5";
    protected static final String SHA = "SHA";

    private static String hash(final String algorithm, final String string) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(string.getBytes());
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.setLength(0);
            final byte digest[] = messageDigest.digest();
            for (final byte b : digest) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, StringHelper.HEX.length()).substring(1));
            }
            return stringBuilder.toString();
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

    public static String md5(final String string) {
        return hash(MD5, string);
    }

    public static String sha(final String string) {
        return hash(SHA, string);
    }

}
