package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.shkschneider.skeleton.helper.LogHelper;

public class ClassHelper {

    protected ClassHelper() {
        // Empty
    }

    @Nullable
    public static Class<?> get(@NonNull final String cls) {
        try {
            return Class.forName(cls);
        }
        catch (final ClassNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String canonicalName(@NonNull final Class<?> cls) {
        return cls.getCanonicalName();
    }

    public static String packageName(@NonNull final Class<?> cls) {
        return canonicalName(cls).replaceFirst("\\.[^\\.]+$", "");
    }

    public static String simpleName(@NonNull final Class<?> cls) {
        return cls.getSimpleName();
    }

}
