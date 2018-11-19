package me.shkschneider.skeleton.javax;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Reflector {

    @Deprecated // Reflection allows programmatic access to information about the methods of loaded classes.
    public static class Field {

        @Nullable
        public static List<String> fields(@NonNull final Object object) {
            return fields(object.getClass());
        }

        @Nullable
        public static List<String> fields(@NonNull final Class<?> cls) {
            try {
                final List<String> fields = new ArrayList<>();
                for (final java.lang.reflect.Field field : cls.getDeclaredFields()) {
                    fields.add(field.getName());
                }
                return fields;
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Nullable
        public static Object field(@NonNull final Object object, @NonNull final String declaredField) {
            return field(object.getClass(), declaredField);
        }

        @Nullable
        public static Object field(@NonNull final Class<?> cls, @NonNull final String declaredField) {
            try {
                java.lang.reflect.Field field = cls.getDeclaredField(declaredField);
                field.setAccessible(true);
                return field.get(cls);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public static boolean field(@NonNull final Object object, @NonNull final String declaredField, final Object value) {
            return field(object.getClass(), declaredField, value);
        }

        public static boolean field(@NonNull final Class<?> cls, @NonNull final String declaredField, final Object value) {
            try {
                java.lang.reflect.Field field = cls.getDeclaredField(declaredField);
                field.setAccessible(true);
                field.set(cls, value);
                return true;
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    @Deprecated // Reflection allows programmatic access to information about the methods of loaded classes.
    public static class Method {

        private static final Object EMPTY_SIGNATURE = (Object) new Class[] {};

        @Nullable
        public static List<String> methods(@NonNull final Object object) {
            return methods(object.getClass());
        }

        @Nullable
        public static Object method(@NonNull final Object object, @NonNull final String declaredMethod, final Class[] signature) {
            return method(object.getClass(), declaredMethod, signature, EMPTY_SIGNATURE);
        }

        @Nullable
        public static Object method(@NonNull final Object object, @NonNull final String declaredMethod, final Class[] signature, final Object ... params) {
            return method(object.getClass(), declaredMethod, signature, params);
        }

        @Nullable
        public static List<String> methods(@NonNull final Class<?> cls) {
            try {
                final List<String> methods = new ArrayList<>();
                for (final java.lang.reflect.Method method : cls.getDeclaredMethods()) {
                    methods.add(method.getName());
                }
                return methods;
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Nullable
        public static Object method(@NonNull final Class<?> cls, @NonNull final String declaredMethod, final Class[] signature) {
            return method(cls, declaredMethod, signature, EMPTY_SIGNATURE);
        }

        @Nullable
        public static Object method(@NonNull final Class<?> cls, @NonNull final String declaredMethod, final Class[] signature, final Object ... params) {
            try {
                java.lang.reflect.Method method = cls.getDeclaredMethod(declaredMethod, signature);
                method.setAccessible(true);
                return method.invoke(cls, params);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
