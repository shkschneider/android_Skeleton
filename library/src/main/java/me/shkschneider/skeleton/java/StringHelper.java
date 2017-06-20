package me.shkschneider.skeleton.java;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.LocaleHelper;

public class StringHelper {

    protected StringHelper() {
        // Empty
    }

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC = "0123456789";
    public static final String HEX = NUMERIC + ALPHA.substring(0, 6);
    public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

    @Deprecated
    public static boolean nullOrEmpty(@Nullable final String string) {
        return TextUtils.isEmpty(string);
    }

    @Deprecated
    public static boolean numeric(@NonNull final String string) {
        return TextUtils.isDigitsOnly(string);
    }

    public static String camelCase(@NonNull final String[] strings) {
        String camelCase = "";
        for (final String string : strings) {
            if (TextUtils.isEmpty(string)) {
                continue ;
            }
            if (TextUtils.isEmpty(camelCase)) {
                camelCase += lower(string);
            }
            else {
                camelCase += capitalize(string);
            }
        }
        return camelCase;
    }

    public static String capitalize(@NonNull final String string) {
        return upper(string.substring(0, 1)) + lower(string.substring(1));
    }

    public static String upper(@NonNull final String string) {
        return string.toUpperCase(LocaleHelper.locale());
    }

    public static String lower(@NonNull final String string) {
        return string.toLowerCase(LocaleHelper.locale());
    }

    public static boolean alpha(@NonNull final String string) {
        return chars(lower(string), ALPHA);
    }

    public static boolean alphanumeric(@NonNull final String string) {
        return chars(lower(string), ALPHA_NUMERIC);
    }

    public static boolean hexadecimal(@NonNull final String string) {
        return chars(lower(string), HEX);
    }

    public static boolean url(@NonNull final String string) {
        return Patterns.WEB_URL.matcher(string).matches();
    }

    public static boolean email(@NonNull final String string) {
        return Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }

    public static boolean phone(@NonNull final String string) {
        return Patterns.PHONE.matcher(string).matches();
    }

    public static int count(@NonNull final String string, @NonNull final String s) {
        return string.length() - string.replace(s, "").length();
    }

    private static boolean chars(@NonNull final String string, @NonNull final String chars) {
        for (char c : string.toCharArray()) {
            if (! chars.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    // <http://stackoverflow.com/a/3758880>
    public static String humanReadableSize(@IntRange(from=0) final long bytes, final boolean binary) {
        final int unit = (binary ? 1024 : 1000);
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        return String.format(LocaleHelper.locale(),
                "%.1f %sB",
                bytes / Math.pow(unit, exp), (binary ? "KMGTPE" : "kMGTPE").charAt(exp-1) + (binary ? "i" : ""));
    }

    public static String[] split(@NonNull final String string) {
        return string.split("(?!^)");
    }

    // <https://stackoverflow.com/a/3322174/603270>
    public static String withoutAccents(@NonNull String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        // string = string.replaceAll("[^\\p{ASCII}]", ""); // ascii
        string = string.replaceAll("\\p{M}", ""); // unicode
        return string;
    }

    public static String[] split(@NonNull final String string, final char delimiter) {
        final List<String> list = new ArrayList<>();
        final int size = string.length();
        int start = 0;
        for (int i = 0; i < size; i++) {
            if (string.charAt(i) == delimiter) {
                if (start < i) {
                    list.add(string.substring(start, i));
                }
                else {
                    list.add("");
                }
                start = i + 1;
            }
            else if (i == size - 1) {
                list.add(string.substring(start, size));
            }
        }
        final String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }

    public static String[] lines(@NonNull final String string, final boolean skipEmptyLines) {
        if (skipEmptyLines) {
            return string.split("[\n\r]+");
        }
        else {
            return string.split("\\r?\\n");
        }
    }

    public static String ellipsize(@NonNull final String string, @IntRange(from=0) final int maxLength) {
        if (string.length() > maxLength) {
            return string.substring(0, maxLength - 3) + "...";
        }
        return string;
    }

    public static String number(final Integer number) {
        return NumberFormat.getNumberInstance(LocaleHelper.locale()).format(number);
    }

    // <http://stackoverflow.com/a/9855338>
    public static String hexadecimal(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length == 0) {
            return "";
        }
        final char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++ ) {
            final int j = bytes[i] & 0xFF;
            chars[i * 2] = HEX.toCharArray()[j >>> 4];
            chars[i * 2 + 1] = HEX.toCharArray()[j & 0x0F];
        }
        return new String(chars);
    }

    // <http://stackoverflow.com/a/4903603>
    public static String repeat(final String s, final Integer n) {
        return new String(new char[n]).replace("\0", s);
    }

}
