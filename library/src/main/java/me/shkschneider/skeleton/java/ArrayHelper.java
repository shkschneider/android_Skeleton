package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class ArrayHelper {

    protected ArrayHelper() {
        // Empty
    }

    private static <T> List<T> list(@NonNull final T[] objects) {
        return Arrays.asList(objects);
    }

    public static <T> boolean contains(@NonNull final T[] objects, @NonNull final T object) {
        return ListHelper.contains(list(objects), object);
    }

    public static <T> T first(@NonNull final T[] objects) {
        return ListHelper.first(list(objects));
    }

    public static <T> T last(@NonNull final T[] objects) {
        return ListHelper.last(list(objects));
    }

}
