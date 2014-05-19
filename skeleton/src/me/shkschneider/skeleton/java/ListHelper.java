package me.shkschneider.skeleton.java;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListHelper {

    public static <T> boolean contains(@NotNull final List<T> objects, @NotNull final T object) {
        return objects.contains(object);
    }

    public static <T> T first(@NotNull final List<T> objects) {
        if (objects.size() == 0) {
            return null;
        }

        return objects.get(0);
    }

    public static <T> T last(@NotNull final List<T> objects) {
        if (objects.size() == 0) {
            return null;
        }

        if (objects.size() == 1) {
            return objects.get(0);
        }
        else {
            return objects.get(objects.size() - 1);
        }
    }

}
