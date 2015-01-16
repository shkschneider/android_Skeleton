package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.java.StringHelper;

public class SharedPreferencesHelper {

    // Public

    private static SharedPreferences getPublic() {
        return PreferenceManager.getDefaultSharedPreferences(ApplicationHelper.context());
    }

    public static boolean putPublic(@NonNull final String key, final String value) {
        LogHelper.debug(key + " = " + value);
        getPublic().edit().putString(key, value).apply();
        return true;
    }

    public static String getPublic(@NonNull final String key, final String defaultValue) {
        return getPublic().getString(key, defaultValue);
    }

    // Private

    private static SharedPreferences getPrivate(@NonNull final String name) {
        if (StringHelper.nullOrEmpty(name)) {
            LogHelper.warning("Name was NULL");
            return getPublic();
        }

        return ApplicationHelper.context().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean putPrivate(@NonNull final String name, @NonNull final String key, final String value) {
        LogHelper.debug(key + " = " + value);
        getPrivate(name).edit().putString(key, value).apply();
        return true;
    }

    public static String getPrivate(@NonNull final String name, @NonNull final String key, final String defaultValue) {
        return getPrivate(name).getString(key, defaultValue);
    }

}
