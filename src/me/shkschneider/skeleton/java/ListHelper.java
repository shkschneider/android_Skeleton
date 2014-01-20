package me.shkschneider.skeleton.java;

import java.util.List;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class ListHelper {

    public static <T> boolean contains(final List<T> objects, final T object) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return false;
        }
        if (object == null) {
            LogHelper.warning("Object was NULL");
            return false;
        }

        return objects.contains(object);
    }

    public static <T> T first(final List<T> objects) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return null;
        }
        if (objects.size() == 0) {
            return null;
        }

        return objects.get(0);
    }

    public static <T> T last(final List<T> objects) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return null;
        }
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
