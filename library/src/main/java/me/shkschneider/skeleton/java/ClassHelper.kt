package me.shkschneider.skeleton.java

import me.shkschneider.skeleton.helper.LogHelper

object ClassHelper {

    fun get(cls: String): Class<*>? {
        try {
            return Class.forName(cls)
        } catch (e: ClassNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun canonicalName(cls: Class<*>): String {
        return cls.canonicalName
    }

    fun packageName(cls: Class<*>): String {
        return canonicalName(cls).replaceFirst("\\.[^\\.]+$".toRegex(), "")
    }

    fun simpleName(cls: Class<*>): String {
        return cls.simpleName
    }

}
