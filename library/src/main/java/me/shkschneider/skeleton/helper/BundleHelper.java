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

    public static Serializable unpack(final Bundle bundle, @NonNull final String key) {
        if (bundle == null) {
            Log.w("Bundle was NULL");
            return null;
        }
        if (! bundle.containsKey(key)) {
            Log.w("Bundle has no such key");
            return null;
        }

        return bundle.getSerializable(key);
    }

}
