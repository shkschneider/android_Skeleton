package me.shkschneider.skeleton.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class JsonParser {

    private JSONObject mJson;

    public JsonParser(final String string) {
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return ;
        }

        try {
            mJson = new JSONObject(string);
        }
        catch (final JSONException e) {
            LogHelper.error("JSONException: " + e.getMessage());
        }
    }

    public JsonParser(final InputStream inputStream) {
        this(new java.util.Scanner(inputStream).useDelimiter("\\A").next());
    }

    public JSONObject object(final String key) {
        if (StringHelper.nullOrEmpty(key)) {
            LogHelper.warning("Key was NULL");
            return null;
        }

        try {
            return mJson.getJSONObject(key);
        }
        catch (final JSONException e) {
            LogHelper.error("JSONException: " + e.getMessage());
            return null;
        }
    }

    public JSONArray array(final String key) {
        if (StringHelper.nullOrEmpty(key)) {
            LogHelper.warning("Key was NULL");
            return null;
        }

        try {
            return mJson.getJSONArray(key);
        }
        catch (final JSONException e) {
            LogHelper.error("JSONException: " + e.getMessage());
            return null;
        }
    }

    public String string(final String key) {
        if (StringHelper.nullOrEmpty(key)) {
            LogHelper.warning("Key was NULL");
            return null;
        }

        try {
            return mJson.getString(key);
        }
        catch (final JSONException e) {
            LogHelper.error("JSONException: " + e.getMessage());
            return null;
        }
    }

}
