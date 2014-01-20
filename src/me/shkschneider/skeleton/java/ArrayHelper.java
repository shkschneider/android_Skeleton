package me.shkschneider.skeleton.java;

import java.util.Arrays;
import java.util.List;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class ArrayHelper {

    private static <T> List<T> list(final T[] objects) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return null;
        }

        return Arrays.asList(objects);
    }

    public static <T> boolean contains(final T[] objects, final T object) {
        return ListHelper.contains(list(objects), object);
    }

    public static <T> T first(final T[] objects) {
        return ListHelper.first(list(objects));
    }

    public static <T> T last(final T[] objects) {
        return ListHelper.last(list(objects));
    }

}
