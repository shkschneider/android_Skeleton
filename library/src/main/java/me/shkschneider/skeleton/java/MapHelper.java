package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHelper {

    public static <K, V> Map<K, V> item(@NonNull final K key, final V value) {
        Map<K, V> item = new HashMap<>();
        item.put(key, value);
        return item;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<K> keys(@NonNull final Map<K, V> objects) {
        return Arrays.asList((K[]) objects.keySet().toArray());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<V> values(@NonNull final Map<K, V> objects) {
        return Arrays.asList((V[]) objects.values().toArray());
    }

    public static <K, V> boolean containsKey(@NonNull final Map<K, V> objects, @NonNull final K key) {
        for (final K o : keys(objects)) {
            if (o.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static <K, V> boolean containsValue(@NonNull final Map<K, V> objects, @NonNull final V value) {
        for (final V o : values(objects)) {
            if (o.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
