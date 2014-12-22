package me.shkschneider.skeleton.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.helpers.LogHelper;

public class SharedPreferencesHelper {

    public static boolean put(final String key, final String value) {
        LogHelper.debug(key + " = " + value);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SkeletonApplication.CONTEXT).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String get(final String key, final String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(SkeletonApplication.CONTEXT).getString(key, defaultValue);
    }

}
