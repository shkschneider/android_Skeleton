package me.shkschneider.skeleton.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import java.util.Set;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.LogHelper;

@SuppressLint("CommitPrefEdits")
public class SharedPreferencesHelper {

    protected SharedPreferencesHelper() {
        // Empty
    }

    private static SharedPreferences get(String name) {
        if (TextUtils.isEmpty(name)) {
            name = ApplicationHelper.packageName();
        }
        return ContextHelper.applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean contains(@NonNull final String key) {
        return get(null).contains(key);
    }

    public static boolean contains(@NonNull final String name, @NonNull final String key) {
        return get(name).contains(key);
    }

    public static void remove(@NonNull final String key) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().remove(key));
    }

    public static void remove(@NonNull final String name, @NonNull final String key) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().remove(key));
    }

    public static void clear() {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().clear());
    }

    public static void clear(@NonNull final String name) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().clear());
    }

    // String

    public static boolean putString(@NonNull final String key, final String value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putString(key, value));
        return true;
    }

    public static boolean putString(@NonNull final String name, @NonNull final String key, final String value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putString(key, value));
        return true;
    }

    @Nullable
    public static String getString(@NonNull final String key, final String defaultValue) {
        return get(null).getString(key, defaultValue);
    }

    @Nullable
    public static String getString(@NonNull final String name, @NonNull final String key, final String defaultValue) {
        return get(name).getString(key, defaultValue);
    }

    // Set<String>

    public static boolean putStringSet(@NonNull final String key, final Set<String> value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putStringSet(key, value));
        return true;
    }

    public static boolean putStringSet(@NonNull final String name, @NonNull final String key, final Set<String> value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putStringSet(key, value));
        return true;
    }

    @Nullable
    public static Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return get(null).getStringSet(key, defaultValue);
    }

    @Nullable
    public static Set<String> getStringSet(@NonNull final String name, @NonNull final String key, final Set<String> defaultValue) {
        return get(name).getStringSet(key, defaultValue);
    }

    // Integer

    public static boolean putInteger(@NonNull final String key, final Integer value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putInt(key, value));
        return true;
    }

    public static boolean putInteger(@NonNull final String name, @NonNull final String key, final Integer value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putInt(key, value));
        return true;
    }

    @Nullable
    public static Integer getInteger(@NonNull final String key, final Integer defaultValue) {
        return get(null).getInt(key, defaultValue);
    }

    @Nullable
    public static Integer getInteger(@NonNull final String name, @NonNull final String key, final Integer defaultValue) {
        return get(name).getInt(key, defaultValue);
    }

    // Long

    public static boolean putLong(@NonNull final String key, final Long value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putLong(key, value));
        return true;
    }

    public static boolean putLong(@NonNull final String name, @NonNull final String key, final Long value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putLong(key, value));
        return true;
    }

    @Nullable
    public static Long getLong(@NonNull final String key, final Long defaultValue) {
        return get(null).getLong(key, defaultValue);
    }

    @Nullable
    public static Long getLong(@NonNull final String name, @NonNull final String key, final Long defaultValue) {
        return get(name).getLong(key, defaultValue);
    }

    // Float

    public static boolean putFloat(@NonNull final String key, final Float value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putFloat(key, value));
        return true;
    }

    public static boolean putFloat(@NonNull final String name, @NonNull final String key, final Float value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putFloat(key, value));
        return true;
    }

    @Nullable
    public static Float getFloat(@NonNull final String key, final Float defaultValue) {
        return get(null).getFloat(key, defaultValue);
    }

    @Nullable
    public static Float getFloat(@NonNull final String name, @NonNull final String key, final Float defaultValue) {
        return get(name).getFloat(key, defaultValue);
    }

    // Boolean

    public static boolean putBoolean(@NonNull final String key, final Boolean value) {
        LogHelper.verbose(key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putBoolean(key, value));
        return true;
    }

    public static boolean putBoolean(@NonNull final String name, @NonNull final String key, final Boolean value) {
        LogHelper.verbose(name + ": " + key + " = " + value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putBoolean(key, value));
        return true;
    }

    @Nullable
    public static Boolean getBoolean(@NonNull final String key, final Boolean defaultValue) {
        return get(null).getBoolean(key, defaultValue);
    }

    @Nullable
    public static Boolean getBoolean(@NonNull final String name, @NonNull final String key, final Boolean defaultValue) {
        return get(name).getBoolean(key, defaultValue);
    }

}
