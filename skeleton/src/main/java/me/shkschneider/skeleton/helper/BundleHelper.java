package me.shkschneider.skeleton.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class BundleHelper {

    public static Bundle pack(@NonNull final String key, final Serializable serializable) {
        final Bundle bundle = new Bundle();
        if (serializable != null) {
            bundle.putSerializable(key, serializable);
        }
        return bundle;
    }

    public static Object unpack(@NonNull final String key, final Bundle bundle) {
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
