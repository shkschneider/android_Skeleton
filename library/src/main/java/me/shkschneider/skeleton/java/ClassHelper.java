package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;

public class ClassHelper {

    protected ClassHelper() {
        // Empty
    }

    public static String canonicalName(@NonNull final Class cls) {
        return cls.getCanonicalName();
    }

    public static String packageName(@NonNull final Class cls) {
        return canonicalName(cls).replaceFirst("\\.[^\\.]+$", "");
    }

    public static String simpleName(@NonNull final Class cls) {
        return cls.getSimpleName();
    }

}
