package me.shkschneider.skeleton.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class HashHelper {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";

    protected static String hash(final String algorithm, final String string, final int length) {
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
        catch (final NoSuchAlgorithmException e) {
            LogHelper.error("NoSuchAlgorithmException: " + e.getMessage());
            return null;
        }
    }

    public static String md5(final String string) {
        return hash(MD5, string, 32);
    }

    public static String sha(final String string) {
        return hash(SHA, string, 40);
    }

}
