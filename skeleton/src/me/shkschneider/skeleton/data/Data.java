package me.shkschneider.skeleton.data;

import android.os.Bundle;

import me.shkschneider.skeleton.helpers.DateTimeHelper;
import me.shkschneider.skeleton.helpers.LogHelper;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Data implements Cloneable {

    private String mTag;
    private Object mData;
    private long mWhen;
    private boolean mProcessed;

    public Data(@NotNull final String tag, final Object data) {
        mTag = tag;
        mData = data;
        mWhen = DateTimeHelper.millitimestamp();
        unprocessed();
    }

    public Data(@NotNull final String tag) {
        this(tag, null);
    }

    public String tag() {
        return mTag;
    }

    public Object data() {
        return mData;
    }

    public long when() {
        return mWhen;
    }

    public long relativeWhen() {
        return (DateTimeHelper.millitimestamp() - mWhen);
    }

    public void unprocessed() {
        mProcessed = false;
    }

    public void processed() {
        mProcessed = true;
    }

    public Object clone() throws CloneNotSupportedException {
        final Data clone = (Data) super.clone();
        clone.mWhen = DateTimeHelper.millitimestamp();
        unprocessed();
        return clone;
    }

    public static Bundle pack(@NotNull final String key, final Serializable serializable) {
        final Bundle bundle = new Bundle();
        if (serializable != null) {
            bundle.putSerializable(key, serializable);
        }
        return bundle;
    }

    public static Object unpack(@NotNull final String key, final Bundle bundle) {
        if (bundle == null) {
            LogHelper.warning("Bundle was NULL");
            return null;
        }
        if (! bundle.containsKey(key)) {
            LogHelper.warning("Bundle has no such key");
            return null;
        }

        return bundle.getSerializable(key);
    }

}
