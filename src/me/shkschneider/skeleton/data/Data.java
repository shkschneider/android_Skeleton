package me.shkschneider.skeleton.data;

import android.os.Bundle;

import java.io.Serializable;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class Data implements Cloneable {

    private String mTag;
    private Object mData;
    private long mWhen;
    private boolean mProcessed;

    public Data(final String tag, final Object data) {
        mTag = tag;
        mData = data;
        mWhen = System.currentTimeMillis();
        unprocessed();
    }

    public Data(final String tag) {
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
        return (System.currentTimeMillis() - mWhen);
    }

    public void unprocessed() {
        mProcessed = false;
    }

    public void processed() {
        mProcessed = true;
    }

    public Object clone() throws CloneNotSupportedException {
        final Data clone = (Data) super.clone();
        clone.mWhen = System.currentTimeMillis();
        unprocessed();
        return clone;
    }

    public static Bundle pack(final String key, final Serializable serializable) {
        if (key == null) {
            LogHelper.warning("Tag was NULL");
            return null;
        }
        if (serializable == null) {
            LogHelper.warning("Serializable was NULL");
            return null;
        }

        final Bundle bundle = new Bundle();
        bundle.putSerializable(key, serializable);
        return bundle;
    }

    public static Object unpack(final String key, final Bundle bundle) {
        if (key == null) {
            LogHelper.warning("Tag was NULL");
            return null;
        }
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
