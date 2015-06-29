package me.shkschneider.skeleton.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class BundleHelper {

    protected BundleHelper() {
        // Empty
    }

    public static Bundle pack(@NonNull final String key, final Serializable serializable) {
        final Bundle bundle = new Bundle();
        if (serializable != null) {
            bundle.putSerializable(key, serializable);
        }
        return bundle;
    }

    public static Serializable unpack(@NonNull final Bundle bundle, @NonNull final String key) {
        if (! bundle.containsKey(key)) {
            LogHelper.w("Bundle has no such key");
            return null;
        }

        return bundle.getSerializable(key);
    }

}
