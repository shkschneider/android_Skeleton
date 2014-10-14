package me.shkschneider.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.shkschneider.app.MainApplication;

public class SharedPreferencesHelper {

    // Public

    private static SharedPreferences getPublic() {
        return PreferenceManager.getDefaultSharedPreferences(MainApplication.CONTEXT);
    }

    public static boolean putPublic(final String key, final String value) {
        LogHelper.debug(key + " = " + value);
        return getPublic().edit().putString(key, value).commit();
    }

    public static String getPublic(final String key, final String defaultValue) {
        return getPublic().getString(key, defaultValue);
    }

    // Private

    private static SharedPreferences getPrivate(final String name) {
        if (StringHelper.nullOrEmpty(name)) {
            LogHelper.warning("Name was NULL");
            return getPublic();
        }

        return MainApplication.CONTEXT.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean putPrivate(final String name, final String key, final String value) {
        LogHelper.debug(key + " = " + value);
        return getPrivate(name).edit().putString(key, value).commit();
    }

    public static String getPrivate(final String name, final String key, final String defaultValue) {
        return getPrivate(name).getString(key, defaultValue);
    }

}
