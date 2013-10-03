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
package me.shkschneider.skeleton.net;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class JsonParser {

    public static JSONObject parse(final String string) {
        if (TextUtils.isEmpty(string)) {
            LogHelper.w("String was NULL");
            return null;
        }

        try {
            return new JSONObject(string);
        }
        catch (JSONException e) {
            LogHelper.e("JSONException: " + e.getMessage());
            return null;
        }
    }

    public static JSONObject jsonObject(final JSONObject json, final String key) {
        if (json == null) {
            LogHelper.w("JSONObject was NULL");
            return null;
        }

        try {
            return json.getJSONObject(key);
        }
        catch (JSONException e) {
            LogHelper.e("JSONException: " + e.getMessage());
            return null;
        }
    }

    public static JSONArray jsonArray(final JSONObject json, final String key) {
        if (json == null) {
            LogHelper.w("JSONObject was NULL");
            return null;
        }
        if (TextUtils.isEmpty(key)) {
            LogHelper.w("Key was NULL");
            return null;
        }

        try {
            return json.getJSONArray(key);
        }
        catch (JSONException e) {
            LogHelper.e("JSONException: " + e.getMessage());
            return null;
        }
    }

    public static String string(final JSONObject json, final String key) {
        if (json == null) {
            LogHelper.w("JSONObject was NULL");
            return null;
        }
        if (TextUtils.isEmpty(key)) {
            LogHelper.w("Key was NULL");
            return null;
        }

        try {
            return json.getString(key);
        }
        catch (JSONException e) {
            LogHelper.e("JSONException: " + e.getMessage());
            return null;
        }
    }

}
