package me.shkschneider.skeleton;

@SuppressWarnings("unused")
public class Runtimes {

    public static Integer processors() {
        return java.lang.Runtime.getRuntime().availableProcessors();
    }

    public static Long freeMemory() {
        return java.lang.Runtime.getRuntime().freeMemory();
    }

    public static Long maxMemory() {
        return java.lang.Runtime.getRuntime().maxMemory();
    }

    public static Long totalMemory() {
        return java.lang.Runtime.getRuntime().totalMemory();
    }

}
