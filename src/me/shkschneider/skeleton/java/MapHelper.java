package me.shkschneider.skeleton.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class MapHelper {

    public static <K, V> Map<K, V> item(final K key, final V value) {
        Map<K, V> item = new HashMap<K, V>();
        item.put(key, value);
        return item;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<K> keys(final Map<K, V> objects) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return null;
        }

        return Arrays.asList((K[]) objects.keySet().toArray());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<V> values(final Map<K, V> objects) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return null;
        }

        return Arrays.asList((V[]) objects.values().toArray());
    }

    public static <K, V> boolean containsKey(final Map<K, V> objects, final K key) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return false;
        }
        if (key == null) {
            LogHelper.warning("Key was NULL");
            return false;
        }

        for (final K o : keys(objects)) {
            if (o.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static <K, V> boolean containsValue(final Map<K, V> objects, final V value) {
        if (objects == null) {
            LogHelper.warning("Objects was NULL");
            return false;
        }
        if (value == null) {
            LogHelper.warning("Value was NULL");
            return false;
        }

        for (final V o : values(objects)) {
            if (o.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
