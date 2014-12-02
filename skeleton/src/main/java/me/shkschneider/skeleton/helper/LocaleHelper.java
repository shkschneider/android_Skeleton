package me.shkschneider.skeleton.helper;

import java.util.Locale;

public class LocaleHelper {

    public static Locale locale() {
        return Locale.getDefault();
    }

    public static String language() {
        return locale().getDisplayLanguage();
    }

    public static String language2() {
        return locale().getLanguage();
    }

    public static String language3() {
        return locale().getISO3Language();
    }

    public static String country() {
        return locale().getDisplayCountry();
    }

    public static String country2() {
        return locale().getCountry();
    }

    public static String country3() {
        return locale().getISO3Country();
    }

}
