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
package me.shkschneider.skeleton.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {

	private static Context CONTEXT = null;

	public static void setContext(final Context context) {
		CONTEXT = context;
	}

    public static Boolean putString(final String key, final String value) {
        if (CONTEXT != null) {
            LogHelper.d(key + " = " + value);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit();
            editor.putString(key, value);
            return editor.commit();
        }
        else {
            LogHelper.w("Context is null");
        }
        return false;
    }

	public static Boolean getBoolean(final String key, final Boolean defaultValue) {
		if (CONTEXT != null) {
			try {
				return PreferenceManager.getDefaultSharedPreferences(CONTEXT).getBoolean(key, defaultValue);
			}
			catch (ClassCastException e) {
				LogHelper.e("ClassCastException: " + e.getMessage());
			}
		}
		else {
			LogHelper.w("Context is null");
		}
		return false;
	}

	public static Boolean putBoolean(final String key, final Boolean value) {
		if (CONTEXT != null) {
			LogHelper.d(key + " = " + value);
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit();
			editor.putBoolean(key, value);
			return editor.commit();
		}
		else {
			LogHelper.w("Context is null");
		}
		return false;
	}

	public static String getString(final String key, final String defaultValue) {
		if (CONTEXT != null) {
			try {
				return PreferenceManager.getDefaultSharedPreferences(CONTEXT).getString(key, defaultValue);
			}
			catch (ClassCastException e) {
				LogHelper.e("ClassCastException: " + e.getMessage());
			}
		}
		else {
			LogHelper.w("Context is null");
		}
		return "";
	}

}
