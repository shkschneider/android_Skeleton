package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.java.StringHelper;

public class SharedPreferencesHelper {

    protected SharedPreferencesHelper() {
        // Empty
    }

    // Public

    private static SharedPreferences getPublic() {
        return PreferenceManager.getDefaultSharedPreferences(ApplicationHelper.context());
    }

    public static boolean putPublic(@NonNull final String key, final String value) {
        Log.d(key + " = " + value);
        getPublic().edit().putString(key, value).apply();
        return true;
    }

    public static String getPublic(@NonNull final String key, final String defaultValue) {
        return getPublic().getString(key, defaultValue);
    }

    // Private

    private static SharedPreferences getPrivate(@NonNull final String name) {
        if (StringHelper.nullOrEmpty(name)) {
            Log.w("Name was NULL");
            return getPublic();
        }

        return ApplicationHelper.context().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean putPrivate(@NonNull final String name, @NonNull final String key, final String value) {
        Log.d(key + " = " + value);
        getPrivate(name).edit().putString(key, value).apply();
        return true;
    }

    public static String getPrivate(@NonNull final String name, @NonNull final String key, final String defaultValue) {
        return getPrivate(name).getString(key, defaultValue);
    }

}
