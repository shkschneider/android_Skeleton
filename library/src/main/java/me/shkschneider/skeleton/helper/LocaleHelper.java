package me.shkschneider.skeleton.helper;

import android.support.annotation.NonNull;
import android.support.v4.os.LocaleListCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleHelper {

    protected LocaleHelper() {
        // Empty
    }

    public static List<Locale> locales() {
        final List<Locale> locales = new ArrayList<>();
        final LocaleListCompat localeListCompat = LocaleListCompat.getDefault();
        for (int i = 0; i < localeListCompat.size(); i++) {
            locales.add(localeListCompat.get(i));
        }
        return locales;
    }

    public static Locale locale() {
        return LocaleListCompat.getDefault().get(0);
    }

    @Deprecated
    public static String language() {
        return language(locale());
    }

    public static String language(@NonNull final Locale locale) {
        return locale.getDisplayLanguage();
    }

    @Deprecated
    public static String language2() {
        return language2(locale());
    }

    public static String language2(@NonNull final Locale locale) {
        return locale.getLanguage();
    }

    @Deprecated
    public static String language3() {
        return language3(locale());
    }

    public static String language3(@NonNull final Locale locale) {
        return locale.getISO3Language();
    }

    @Deprecated
    public static String country() {
        return country(locale());
    }

    public static String country(@NonNull final Locale locale) {
        return locale.getDisplayCountry();
    }

    @Deprecated
    public static String country2() {
        return country2(locale());
    }

    public static String country2(@NonNull final Locale locale) {
        return locale.getCountry();
    }

    @Deprecated
    public static String country3() {
        return country3(locale());
    }

    public static String country3(@NonNull final Locale locale) {
        return locale.getISO3Country();
    }

}
