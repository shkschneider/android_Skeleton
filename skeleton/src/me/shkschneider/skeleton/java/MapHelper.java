package me.shkschneider.skeleton.java;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHelper {

    public static <K, V> Map<K, V> item(@NotNull final K key, final V value) {
        Map<K, V> item = new HashMap<K, V>();
        item.put(key, value);
        return item;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<K> keys(@NotNull final Map<K, V> objects) {
        return Arrays.asList((K[]) objects.keySet().toArray());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<V> values(@NotNull final Map<K, V> objects) {
        return Arrays.asList((V[]) objects.values().toArray());
    }

    public static <K, V> boolean containsKey(@NotNull final Map<K, V> objects, @NotNull final K key) {
        for (final K o : keys(objects)) {
            if (o.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static <K, V> boolean containsValue(@NotNull final Map<K, V> objects, @NotNull final V value) {
        for (final V o : values(objects)) {
            if (o.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
