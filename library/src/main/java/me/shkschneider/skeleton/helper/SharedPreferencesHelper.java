package me.shkschneider.skeleton.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.Map;
import java.util.Set;

import me.shkschneider.skeleton.java.MapHelper;

public class SharedPreferencesHelper {

    protected SharedPreferencesHelper() {
        // Empty
    }

    private static SharedPreferences get() {
        return ApplicationHelper.context().getSharedPreferences(ApplicationHelper.name(), Context.MODE_PRIVATE);
    }

    public static boolean contains(@NonNull final String key) {
        return get().contains(key);
    }

    public static void remove(@NonNull final String key) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().remove(key));
    }

    public static void clear() {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().clear());
    }

    public static void dump() {
        final Map<String, ?> map = get().getAll();
        for (final String key : MapHelper.keys(map)) {
            LogHelper.debug(key + "=" + map.get(key).toString());
        }
    }

    // String

    public static boolean put(@NonNull final String key, final String value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putString(key, value));
        return true;
    }

    public static String get(@NonNull final String key, final String defaultValue) {
        return get().getString(key, defaultValue);
    }

    // Set<String>

    public static boolean put(@NonNull final String key, final Set<String> value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putStringSet(key, value));
        return true;
    }

    public static Set<String> get(@NonNull final String key, final Set<String> defaultValue) {
        return get().getStringSet(key, defaultValue);
    }

    // Int

    public static boolean put(@NonNull final String key, final Integer value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putInt(key, value));
        return true;
    }

    public static Integer get(@NonNull final String key, final Integer defaultValue) {
        return get().getInt(key, defaultValue);
    }

    // Long

    public static boolean put(@NonNull final String key, final Long value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putLong(key, value));
        return true;
    }

    public static Long get(@NonNull final String key, final Long defaultValue) {
        return get().getLong(key, defaultValue);
    }

    // Float

    public static boolean put(@NonNull final String key, final Float value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putFloat(key, value));
        return true;
    }

    public static Float get(@NonNull final String key, final Float defaultValue) {
        return get().getFloat(key, defaultValue);
    }

    // Boolean

    public static boolean put(@NonNull final String key, final Boolean value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putBoolean(key, value));
        return true;
    }

    public static Boolean get(@NonNull final String key, final Boolean defaultValue) {
        return get().getBoolean(key, defaultValue);
    }

}
