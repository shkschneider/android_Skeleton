package me.shkschneider.skeleton.helpers;

@SuppressWarnings("unused")
public final class RuntimeHelper {

    public static int processors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

}
