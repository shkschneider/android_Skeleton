package me.shkschneider.skeleton;

import android.text.TextUtils;

public class Strings {

    public static String capitalize(final String string) {
        if (! TextUtils.isEmpty(string)) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
        }
        else {
            Log.w("String was NULL");
        }
        return string;
    }

    public static Boolean numeric(final String string) {
        if (! TextUtils.isEmpty(string)) {
            return TextUtils.isDigitsOnly(string);
        }
        else {
            Log.w("String was NULL");
        }
        return false;
    }

    public static Boolean contains(final String[] strings, final String string) {
        if (strings != null) {
            for (final String s : strings) {
                if (s.equals(string)) {
                    return true;
                }
            }
        }
        else {
            Log.w("Strings was NULL");
        }
        return false;
    }

}
