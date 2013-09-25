package me.shkschneider.skeleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressWarnings("unused")
public class Preferences {

    public static Boolean putString(final Context context, final String key, final String value) {
        if (context != null) {
            Skeleton.Log.d(key + " = " + value);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString(key, value);
            return editor.commit();
        }
        else {
            Skeleton.Log.w("Context was NULL");
        }
        return false;
    }

    public static String getString(final Context context, final String key, final String defaultValue) {
        if (context != null) {
            try {
                return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
            }
            catch (ClassCastException e) {
                Skeleton.Log.e("ClassCastException: " + e.getMessage());
            }
        }
        else {
            Skeleton.Log.w("Context was NULL");
        }
        return "";
    }

}
