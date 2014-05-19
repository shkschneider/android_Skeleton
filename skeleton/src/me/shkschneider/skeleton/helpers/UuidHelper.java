package me.shkschneider.skeleton.helpers;

import me.shkschneider.skeleton.java.StringHelper;

import java.util.UUID;

public class UuidHelper {

    public static UUID uuid() {
        final String id = AndroidHelper.id();
        if (StringHelper.nullOrEmpty(id)) {
            LogHelper.warning("Id was NULL");
            return null;
        }

        return UUID.nameUUIDFromBytes(id.getBytes());
    }

    public static UUID randomUuid() {
        return UUID.randomUUID();
    }

}
