package me.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonParser {

    public static JsonObject parse(@NotNull final String string) {
        try {
            final Gson gson = new Gson();
            return gson.fromJson(string, JsonObject.class);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static JsonObject parse(@NotNull final InputStream inputStream) {
        final String string = FileHelper.readString(inputStream);
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        return parse(string);
    }

    public static List<String> keys(@NotNull final JsonObject jsonObject) {
        final List<String> keys = new ArrayList<String>();
        try {
            for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                keys.add(entry.getKey());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return keys;
    }

    public static List<JsonElement> values(@NotNull final JsonObject jsonObject) {
        final List<JsonElement> values = new ArrayList<JsonElement>();
        try {
            for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                values.add(entry.getValue());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        return values;
    }

    public static boolean has(@NotNull final JsonObject jsonObject, @NotNull final String key) {
        try {
            return jsonObject.has(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return false;
        }
    }

    public static JsonObject object(@NotNull final JsonObject jsonObject, @NotNull final String key) {
        try {
            return jsonObject.getAsJsonObject(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static JsonArray array(@NotNull final JsonObject jsonObject, @NotNull final String key) {
        try {
            return jsonObject.getAsJsonArray(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String string(@NotNull final JsonObject jsonObject, @NotNull final String key) {
        try {
            return jsonObject.get(key).getAsString();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
