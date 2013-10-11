/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class SharedPreferencesHelper {

    public static Boolean put(final Context context, final String key, final String value) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        LogHelper.d(key + " = " + value);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String get(final Context context, final String key, final String defaultValue) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        try {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
        }
        catch (ClassCastException e) {
            LogHelper.e("ClassCastException: " + e.getMessage());
            return null;
        }
    }

}
