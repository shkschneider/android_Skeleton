package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class ListHelper {

    protected ListHelper() {
        // Empty
    }

    public static <T> boolean contains(@NonNull final List<T> objects, @NonNull final T object) {
        return objects.contains(object);
    }

    @Nullable
    public static <T> T first(@NonNull final List<T> objects) {
        if (objects.size() == 0) {
            return null;
        }

        return objects.get(0);
    }

    @Nullable
    public static <T> T last(@NonNull final List<T> objects) {
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
