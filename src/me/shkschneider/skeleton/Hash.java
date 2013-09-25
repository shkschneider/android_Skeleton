package me.shkschneider.skeleton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class Hash {

    public static final String MD5 = "MD5";
    public static final String SHA = "SHA";
    public static final Integer MD5_LENGTH = 32;
    public static final Integer SHA_LENGTH = 40;

    protected static String hash(final String algorithm, final String string, final Integer length) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(string.getBytes());

            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.setLength(0);

            final byte digest[] = messageDigest.digest();
            for (final byte d : digest) {
                final int b = d & 255;
                if (b < length) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(b));
            }
            return stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("NoSuchAlgorithmException: " + e.getMessage());
        }
        return null;
    }

    public static String md5(final String string) {
        return hash(MD5, string, MD5_LENGTH);
    }

    public static String sha(final String string) {
        return hash(SHA, string, SHA_LENGTH);
    }

}
