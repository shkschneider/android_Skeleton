package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.LogHelper;

@Deprecated // Discouraged
public class ReflectHelper {

    public static class Field {

        @Nullable
        public static List<String> fields(@NonNull final Object object) {
            try {
                final List<String> fields = new ArrayList<>();
                for (final java.lang.reflect.Field field : object.getClass().getDeclaredFields()) {
                    fields.add(field.getName());
                }
                return fields;
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

        @Nullable
        public static Object field(@NonNull final Object object, @NonNull final String declaredField) {
            try {
                java.lang.reflect.Field field = object.getClass().getDeclaredField(declaredField);
                field.setAccessible(true);
                return field.get(object);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

        @Nullable
        public static boolean field(@NonNull final Object object, @NonNull final String declaredField, final Object value) {
            try {
                java.lang.reflect.Field field = object.getClass().getDeclaredField(declaredField);
                field.setAccessible(true);
                field.set(object, value);
                return true;
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return false;
        }

    }

    public static class Method {

        @Nullable
        public static List<String> methods(@NonNull final Object object) {
            try {
                final List<String> methods = new ArrayList<>();
                for (final java.lang.reflect.Method method : object.getClass().getDeclaredMethods()) {
                    methods.add(method.getName());
                }
                return methods;
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

        @Nullable
        public static Object method(@NonNull final Object object, @NonNull final String declaredMethod, final Class[] signature) {
            try {
                java.lang.reflect.Method method = object.getClass().getDeclaredMethod(declaredMethod, signature);
                method.setAccessible(true);
                return method.invoke(null, (Object) new Class[] {});
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

        @Nullable
        public static Object method(@NonNull final Object object, @NonNull final String declaredMethod, final Class[] signature, final Object ... params) {
            try {
                java.lang.reflect.Method method = object.getClass().getDeclaredMethod(declaredMethod, signature);
                method.setAccessible(true);
                return method.invoke(object, params);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

    }

}
