package me.shkschneider.skeleton.java;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class ArrayHelper {

    protected ArrayHelper() {
        // Empty
    }

    @IntRange(from=-1)
    public static <T> int index(@NonNull final T[] objects, @NonNull final T object) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> List<T> list(@NonNull final T[] objects) {
        return Arrays.asList(objects);
    }

    public static <T> boolean contains(@NonNull final T[] objects, @NonNull final T object) {
        return ListHelper.contains(list(objects), object);
    }

    @Nullable
    public static <T> T first(@NonNull final T[] objects) {
        return ListHelper.first(list(objects));
    }

    @Nullable
    public static <T> T last(@NonNull final T[] objects) {
        return ListHelper.last(list(objects));
    }

}
