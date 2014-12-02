package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class JsonParser {

    public static JSONObject parse(@NonNull final String string) {
        try {
            return new JSONObject(string);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static JSONObject parse(@NonNull final InputStream inputStream) {
        final String string = new Scanner(inputStream).useDelimiter("\\A").next();
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        return parse(string);
    }

    public static List<String> keys(@NonNull final JSONObject jsonObject) {
        final List<String> keys = new ArrayList<String>();
        try {
            final Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                keys.add((String) iterator.next());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return keys;
    }

    public static List<Object> values(@NonNull final JSONObject jsonObject) {
        final List<Object> values = new ArrayList<Object>();
        final List<String> keys = keys(jsonObject);
        for (final String key : keys) {
            try {
                values.add(jsonObject.get(key));
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
        }
        return values;
    }

    public static boolean has(@NonNull final JSONObject JSONObject, @NonNull final String key) {
        try {
            return JSONObject.has(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static JSONObject object(@NonNull final JSONObject JSONObject, @NonNull final String key) {
        try {
            return JSONObject.getJSONObject(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static JSONArray array(@NonNull final JSONObject JSONObject, @NonNull final String key) {
        try {
            return JSONObject.getJSONArray(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String string(@NonNull final JSONObject JSONObject, @NonNull final String key) {
        try {
            return JSONObject.getString(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
