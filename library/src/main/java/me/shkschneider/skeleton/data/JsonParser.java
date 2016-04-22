package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class JsonParser {

    protected JsonParser() {
        // Empty
    }

    @Nullable
    public static JSONObject parse(@NonNull final String string) {
        try {
            return new JSONObject(string);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static JSONObject parse(@NonNull final InputStream inputStream) {
        final String string = FileHelper.readString(inputStream);
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        return parse(string);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T get(@NonNull final JSONObject jsonObject, @NonNull final String key, final T fallback) {
        try {
            return (T) jsonObject.get(key);
        }
        catch (final Exception e) {
            return fallback;
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T get(@NonNull final JSONArray jsonArray, final int index, final T fallback) {
        try {
            return (T) jsonArray.get(index);
        }
        catch (final Exception e) {
            return fallback;
        }
    }

    public static List<String> keys(@NonNull final JSONObject jsonObject) {
        final List<String> keys = new ArrayList<>();
        try {
            final Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                keys.add(iterator.next());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return keys;
    }

    public static List<Object> values(@NonNull final JSONObject jsonObject) {
        final List<Object> values = new ArrayList<>();
        try {
            for (final String key : keys(jsonObject)) {
                values.add(jsonObject.get(key));
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return values;
    }

    public static boolean has(@NonNull final JSONObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.has(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

}
