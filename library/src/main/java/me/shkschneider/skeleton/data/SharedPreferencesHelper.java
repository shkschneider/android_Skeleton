package me.shkschneider.skeleton.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.Set;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;

@SuppressLint("CommitPrefEdits")
public class SharedPreferencesHelper {

    protected SharedPreferencesHelper() {
        // Empty
    }

    private static SharedPreferences get() {
        return ApplicationHelper.context().getSharedPreferences(ApplicationHelper.packageName(), Context.MODE_PRIVATE);
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

    // String

    public static boolean putString(@NonNull final String key, final String value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putString(key, value));
        return true;
    }

    @Nullable
    public static String getString(@NonNull final String key, final String defaultValue) {
        return get().getString(key, defaultValue);
    }

    // Set<String>

    public static boolean putStringSet(@NonNull final String key, final Set<String> value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putStringSet(key, value));
        return true;
    }

    @Nullable
    public static Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return get().getStringSet(key, defaultValue);
    }

    // Integer

    public static boolean putInteger(@NonNull final String key, final Integer value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putInt(key, value));
        return true;
    }

    @Nullable
    public static Integer getInteger(@NonNull final String key, final Integer defaultValue) {
        return get().getInt(key, defaultValue);
    }

    // Long

    public static boolean putLong(@NonNull final String key, final Long value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putLong(key, value));
        return true;
    }

    @Nullable
    public static Long getLong(@NonNull final String key, final Long defaultValue) {
        return get().getLong(key, defaultValue);
    }

    // Float

    public static boolean putFloat(@NonNull final String key, final Float value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putFloat(key, value));
        return true;
    }

    @Nullable
    public static Float getFloat(@NonNull final String key, final Float defaultValue) {
        return get().getFloat(key, defaultValue);
    }

    // Boolean

    public static boolean putBoolean(@NonNull final String key, final Boolean value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get().edit().putBoolean(key, value));
        return true;
    }

    @Nullable
    public static Boolean getBoolean(@NonNull final String key, final Boolean defaultValue) {
        return get().getBoolean(key, defaultValue);
    }

}
