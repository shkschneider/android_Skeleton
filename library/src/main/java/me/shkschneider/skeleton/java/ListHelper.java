package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.util.List;

public class ListHelper {

    protected ListHelper() {
        // Empty
    }

    public static <T> boolean contains(@NonNull final List<T> objects, @NonNull final T object) {
        return objects.contains(object);
    }

    public static <T> T first(@Size(min=1) @NonNull final List<T> objects) {
        return objects.get(0);
    }

    public static <T> T last(@Size(min=1) @NonNull final List<T> objects) {
        return objects.get(objects.size() - 1);
    }

}
