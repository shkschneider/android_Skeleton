package me.shkschneider.skeleton.java;

public class ClassHelper {

    protected ClassHelper() {
        // Empty
    }

    public static String canonicalName(final Class cls) {
        return cls.getCanonicalName();
    }

    public static String packageName(final Class cls) {
        return canonicalName(cls).replaceFirst("\\.[^\\.]+$", "");
    }

    public static String simpleName(final Class cls) {
        return cls.getSimpleName();
    }

}
