package me.shkschneider.sdk;

import java.util.Random;

public class StringHelper {

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC = "0123456789";
    public final static String HEX = NUMERIC + ALPHA.substring(0, 6);
    public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

    public static boolean nullOrEmpty(final String string) {
        return (string == null || (string.length() == 0));
    }

    public static String capitalize(final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String upper(final String string) {
        return string.toUpperCase();
    }

    public static String lower(final String string) {
        return string.toLowerCase();
    }

    public static boolean alpha(final String string) {
        return chars(string.toLowerCase(), ALPHA);
    }

    public static boolean numeric(final String string) {
        return chars(string.toLowerCase(), NUMERIC);
    }

    public static boolean alphanumeric(final String string) {
        return chars(string.toLowerCase(), ALPHA_NUMERIC);
    }

    private static boolean chars(final String string, final String chars) {
        for (char c : string.toCharArray()) {
            if (! chars.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    public static String random(final String characters, final int length) {
        if (length <= 0) {
            LogHelper.warning("Length was invalid");
            return null;
        }

        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    public static String random(final int length) {
        return random(HEX.toLowerCase(), length);
    }

}
