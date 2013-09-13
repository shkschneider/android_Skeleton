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
import android.text.TextUtils;

import me.shkschneider.skeleton.SkeletonLog;

public class SkeletonPreferencesHelper {

	private static SkeletonPreferencesHelper INSTANCE = null;

    private Context mContext;

    public static SkeletonPreferencesHelper newInstance(final Context context) {
        if (INSTANCE == null) {
            if (context != null) {
                INSTANCE = new SkeletonPreferencesHelper(context);
            }
            else {
                SkeletonLog.w("Context was NULL");
            }
        }
        return INSTANCE;
    }

    private SkeletonPreferencesHelper(final Context context) {
        mContext = context;
    }

    private Boolean putString(final String key, final String value) {
        if (! TextUtils.isEmpty(key)) {
            SkeletonLog.d(key + " = " + value);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            editor.putString(key, value);
            return editor.commit();
        }
        else {
            SkeletonLog.d("Key was NULL");
        }
        return false;
    }

    private Boolean putInteger(final String key, final Integer value) {
        if (! TextUtils.isEmpty(key)) {
            SkeletonLog.d(key + " = " + value);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        else {
            SkeletonLog.d("Key was NULL");
        }
        return false;
    }

    private Boolean putBoolean(final String key, final Boolean value) {
        if (! TextUtils.isEmpty(key)) {
            SkeletonLog.d(key + " = " + value);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        else {
            SkeletonLog.d("Key was NULL");
        }
        return false;
    }

    private String getString(final String key, final String defaultValue) {
        try {
            return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, defaultValue);
        }
        catch (ClassCastException e) {
            SkeletonLog.e("ClassCastException: " + e.getMessage());
        }
        return "";
    }

    private Integer getInteger(final String key, final Integer defaultValue) {
        try {
            return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, defaultValue);
        }
        catch (ClassCastException e) {
            SkeletonLog.e("ClassCastException: " + e.getMessage());
        }
        return 0;
    }

    private Boolean getBoolean(final String key, final Boolean defaultValue) {
        try {
            return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, defaultValue);
        }
        catch (ClassCastException e) {
            SkeletonLog.e("ClassCastException: " + e.getMessage());
        }
		return false;
	}

}
