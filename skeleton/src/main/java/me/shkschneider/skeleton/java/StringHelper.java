package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import java.text.Normalizer;
import java.util.Random;

import me.shkschneider.skeleton.helper.LogHelper;

public class StringHelper {

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC = "0123456789";
    public final static String HEX = NUMERIC + ALPHA.substring(0, 6);
    public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

    public static boolean nullOrEmpty(final String string) {
        return (string == null || (string.length() == 0));
    }

    public static String capitalize(@NonNull final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String upper(@NonNull final String string) {
        return string.toUpperCase();
    }

    public static String lower(@NonNull final String string) {
        return string.toLowerCase();
    }

    public static boolean alpha(@NonNull final String string) {
        return chars(string.toLowerCase(), ALPHA);
    }

    public static boolean numeric(@NonNull final String string) {
        return chars(string.toLowerCase(), NUMERIC);
    }

    public static boolean alphanumeric(@NonNull final String string) {
        return chars(string.toLowerCase(), ALPHA_NUMERIC);
    }

    public static boolean url(@NonNull final String string) {
        return Patterns.WEB_URL.matcher(string).matches();
    }

    public static boolean email(@NonNull final String string) {
        return Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }

    public static boolean phone(@NonNull final String string) {
        return PhoneNumberUtils.isGlobalPhoneNumber(string);
    }

    private static boolean chars(@NonNull final String string, @NonNull final String chars) {
        for (char c : string.toCharArray()) {
            if (! chars.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    public static String[] split(@NonNull final String string) {
        return string.split("(?!^)");
    }

    public static String withoutAccents(@NonNull String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        // string = string.replaceAll("[^\\p{ASCII}]", ""); // ascii
        string = string.replaceAll("\\p{M}", ""); // unicode
        return string;
    }

    public static String random(@NonNull final String characters, final int length) {
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
