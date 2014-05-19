package me.shkschneider.skeleton.data;

import me.shkschneider.skeleton.SkeletonException;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.StringHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class JsonParser {

    private JSONObject mJson;

    public JsonParser(@NotNull final String string) throws SkeletonException {
        try {
            mJson = new JSONObject(string);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            throw new SkeletonException(e);
        }
    }

    public JsonParser(@NotNull final InputStream inputStream) throws SkeletonException {
        final String string = new Scanner(inputStream).useDelimiter("\\A").next();
        if (StringHelper.nullOrEmpty(string)) {
            LogHelper.warning("String was NULL");
            throw new SkeletonException("String was NULL");
        }

        try {
            mJson = new JSONObject(string);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            throw new SkeletonException(e);
        }
    }

    public JSONObject object(@NotNull final String key) {
        try {
            return mJson.getJSONObject(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public JSONArray array(@NotNull final String key) {
        try {
            return mJson.getJSONArray(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public String string(@NotNull final String key) {
        try {
            return mJson.getString(key);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

}
