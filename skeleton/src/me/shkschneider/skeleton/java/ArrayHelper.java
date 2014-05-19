package me.shkschneider.skeleton.java;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ArrayHelper {

    private static <T> List<T> list(@NotNull final T[] objects) {
        return Arrays.asList(objects);
    }

    public static <T> boolean contains(@NotNull final T[] objects, @NotNull final T object) {
        return ListHelper.contains(list(objects), object);
    }

    public static <T> T first(@NotNull final T[] objects) {
        return ListHelper.first(list(objects));
    }

    public static <T> T last(@NotNull final T[] objects) {
        return ListHelper.last(list(objects));
    }

}
