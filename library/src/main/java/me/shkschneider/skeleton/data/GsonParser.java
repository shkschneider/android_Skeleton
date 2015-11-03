package me.shkschneider.skeleton.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

public class GsonParser {

    protected GsonParser() {
        // Empty
    }

    @Nullable
    public static JsonObject parse(@NonNull final String string) {
        try {
            final Gson gson = new Gson();
            return gson.fromJson(string, JsonObject.class);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    @Nullable
    public static JsonObject parse(@NonNull final InputStream inputStream) {
        final String string = FileHelper.readString(inputStream);
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            return null;
        }

        return parse(string);
    }

    @Nullable
    public static JsonElement get(@NonNull final JsonObject jsonObject, @NonNull final String string) {
        // bad formats
        if (StringHelper.count(string, "\\{") != StringHelper.count(string, "\\}")) {
            LogHelper.error("Bad {} format: " + string);
            return null;
        }
        if (StringHelper.count(string, "\\[") != StringHelper.count(string, "\\]")) {
            LogHelper.error("Bad [] format: " + string);
            return null;
        }
        if (! string.matches("^([\\{|\\[][^\\{|\\[|\\}|\\]]+(:[0-9]+)?[\\}|\\]])+$")) {
            LogHelper.error("Bad format: " + string);
            return null;
        }
        // tags and paths
        final Matcher matcherTags = Pattern.compile("([\\{|\\[])").matcher(string);
        final List<String> tags = new ArrayList<>();
        while (matcherTags.find()) {
            tags.add(matcherTags.group());
        }
        LogHelper.verbose("Tags: " + tags.toString());
        final Matcher matcherPaths = Pattern.compile("[\\{|\\[]([^\\{|\\[|\\}|\\]]+)[\\}|\\]]").matcher(string);
        final List<String> paths = new ArrayList<>();
        while (matcherPaths.find()) {
            paths.add(matcherPaths.group(1));
        }
        LogHelper.verbose("Paths: " + paths.toString());
        // loop
        JsonObject object = jsonObject;
        for (int i = 0; i < tags.size(); i++) {
            // b triggers the end
            final boolean b = (i == (tags.size() - 1));
            final String t = tags.get(i);
            final String p = paths.get(i);
            // forced end
            if (object.isJsonNull()) {
                LogHelper.warning("Reached a JsonNull");
                return null;
            }
            // object
            if (t.equals("{")) {
                if (! object.has(p)) {
                    LogHelper.error("No such object path: " + p);
                    return null;
                }
                final JsonElement jsonElement = object.get(p);
                // found the result
                if (b) {
                    return object.get(p);
                }
                // keep going so check the type
                if (! jsonElement.isJsonObject()) {
                    LogHelper.error("Invalid type (not JsonObject): " + p);
                    return null;
                }
                // prepare next loop
                object = object.getAsJsonObject(p);
            }
            // array
            else if (t.equals("[")) {
                final String[] d = p.split(":");
                // without index
                if (d.length == 1) {
                    final String s = d[0];
                    if (! object.has(s)) {
                        LogHelper.error("No such array path: " + p);
                        return null;
                    }
                    // has to be an array
                    if (! object.isJsonArray()) {
                        LogHelper.error("Invalid type (not JsonArray): " + p);
                        return null;
                    }
                    // returns as nothing more can be done
                    return object.getAsJsonArray(s);
                }
                // with index
                else if (d.length == 2) {
                    final String s = d[0];
                    if (! object.has(s)) {
                        LogHelper.error("No such array path: " + p);
                        return null;
                    }
                    if (! StringHelper.numeric(d[1])) {
                        LogHelper.error("Invalid index: " + d[1]);
                        return null;
                    }
                    final int n = Integer.valueOf(d[1]);
                    if (n >= d.length) {
                        LogHelper.error("Invalid index: " + n);
                        return null;
                    }
                    final JsonElement jsonElement = object.get(s);
                    final JsonArray array = jsonElement.getAsJsonArray();
                    // found the result
                    if (b) {
                        return array.get(n);
                    }
                    // keep going so check the type
                    if (! jsonElement.isJsonArray()) {
                        LogHelper.error("Invalid type (not JsonArray): " + s);
                        return null;
                    }
                    // prepare next loop
                    object = array.get(n).getAsJsonObject();
                }
                else {
                    LogHelper.error("Bad format: " + p);
                    return null;
                }
            }
        }
        return object;
    }

    @Nullable
    public static JsonElement element(@NonNull final JsonObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.get(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static List<String> keys(@NonNull final JsonObject jsonObject) {
        final List<String> keys = new ArrayList<>();
        try {
            for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                keys.add(entry.getKey());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
        }
        return keys;
    }

    public static List<JsonElement> values(@NonNull final JsonObject jsonObject) {
        final List<JsonElement> values = new ArrayList<>();
        try {
            for (final Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                values.add(entry.getValue());
            }
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
        }
        return values;
    }

    public static boolean has(@NonNull final JsonObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.has(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return false;
        }
    }

    @Nullable
    public static JsonObject object(@NonNull final JsonObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.getAsJsonObject(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    @Nullable
    public static JsonArray array(@NonNull final JsonObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.getAsJsonArray(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    @Nullable
    public static String string(@NonNull final JsonObject jsonObject, @NonNull final String key) {
        try {
            return jsonObject.get(key).getAsString();
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

}
