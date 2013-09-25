package me.shkschneider.skeleton;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class JsonParser {

    public static JSONObject parse(final String string) {
        if (! TextUtils.isEmpty(string)) {
            try {
                return new JSONObject(string);
            }
            catch (JSONException e) {
                Skeleton.Log.e("JSONException: " + e.getMessage());
            }
        }
        else {
            Skeleton.Log.w("String was NULL");
        }
        return null;
    }

    public static JSONObject getJsonObject(final JSONObject json, final String key) {
        if (json != null) {
            try {
                return json.getJSONObject(key);
            }
            catch (JSONException e) {
                Skeleton.Log.e("JSONException: " + e.getMessage());
            }
        }
        else {
            Skeleton.Log.w("JSONObject was NULL");
        }
        return null;
    }

    public static JSONArray getJsonArray(final JSONObject json, final String key) {
        if (json != null) {
            try {
                return json.getJSONArray(key);
            }
            catch (JSONException e) {
                Skeleton.Log.e("JSONException: " + e.getMessage());
            }
        }
        else {
            Skeleton.Log.w("JSONObject was NULL");
        }
        return null;
    }

    public static String getString(final JSONObject json, final String key) {
        if (json != null) {
            try {
                return json.getString(key);
            }
            catch (JSONException e) {
                Skeleton.Log.e("JSONException: " + e.getMessage());
            }
        }
        else {
            Skeleton.Log.w("JSONObject was NULL");
        }
        return null;
    }

}
