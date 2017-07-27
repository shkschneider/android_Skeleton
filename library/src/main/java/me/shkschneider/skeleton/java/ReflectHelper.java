package me.shkschneider.skeleton.java;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Modifier;
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
                if (Modifier.isStatic(field.getModifiers())) {
                    return Static.field(object.getClass(), declaredField);
                }
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
                if (Modifier.isStatic(field.getModifiers())) {
                    return Static.field(object.getClass(), declaredField, value);
                }
                field.setAccessible(true);
                field.set(object, value);
                return true;
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return false;
        }

        public static class Static {

            @Nullable
            public static List<String> fields(@NonNull final Class cls) {
                try {
                    final List<String> fields = new ArrayList<>();
                    for (final java.lang.reflect.Field field : cls.getClass().getDeclaredFields()) {
                        if (Modifier.isStatic(field.getModifiers())) {
                            fields.add(field.getName());
                        }
                    }
                    return fields;
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

            @Nullable
            public static Object field(@NonNull final Class cls, @NonNull final String declaredField) {
                try {
                    java.lang.reflect.Field field = cls.getClass().getDeclaredField(declaredField);
                    if (! Modifier.isStatic(field.getModifiers())) {
                        return null;
                    }
                    field.setAccessible(true);
                    return field.get(null);
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

            @Nullable
            public static boolean field(@NonNull final Class cls, @NonNull final String declaredField, final Object value) {
                try {
                    java.lang.reflect.Field field = cls.getClass().getDeclaredField(declaredField);
                    if (! Modifier.isStatic(field.getModifiers())) {
                        return false;
                    }
                    field.setAccessible(true);
                    field.set(null, value);
                    return true;
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return false;
            }

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
                if (Modifier.isStatic(method.getModifiers())) {
                    return Static.method(object.getClass(), declaredMethod, signature);
                }
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
                if (Modifier.isStatic(method.getModifiers())) {
                    return Static.method(object.getClass(), declaredMethod, signature, params);
                }
                method.setAccessible(true);
                return method.invoke(object, params);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
            return null;
        }

        public static class Static {

            @Nullable
            public static List<String> methods(@NonNull final Class cls) {
                try {
                    final List<String> methods = new ArrayList<>();
                    for (final java.lang.reflect.Method method : cls.getClass().getDeclaredMethods()) {
                        if (Modifier.isStatic(method.getModifiers())) {
                            methods.add(method.getName());
                        }
                    }
                    return methods;
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

            @Nullable
            public static Object method(@NonNull final Class cls, @NonNull final String declaredMethod, final Class[] signature) {
                try {
                    java.lang.reflect.Method method = cls.getClass().getDeclaredMethod(declaredMethod, signature);
                    if (! Modifier.isStatic(method.getModifiers())) {
                        return null;
                    }
                    method.setAccessible(true);
                    return method.invoke(null, (Object[]) null);
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

            @Nullable
            public static Object method(@NonNull final Class cls, @NonNull final String declaredMethod, final Class[] signature, final Object ... params) {
                try {
                    java.lang.reflect.Method method = cls.getClass().getDeclaredMethod(declaredMethod, signature);
                    if (! Modifier.isStatic(method.getModifiers())) {
                        return null;
                    }
                    method.setAccessible(true);
                    return method.invoke(null, params);
                }
                catch (final Exception e) {
                    LogHelper.wtf(e);
                }
                return null;
            }

        }

    }

}
