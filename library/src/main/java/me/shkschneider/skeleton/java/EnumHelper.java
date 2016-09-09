package me.shkschneider.skeleton.java;

import java.security.SecureRandom;

public class EnumHelper {

    // <http://stackoverflow.com/a/14257525>
    public static <T extends Enum<?>> T random(final Class<T> cls) {
        return cls.getEnumConstants()[new SecureRandom().nextInt(cls.getEnumConstants().length)];
    }

}
