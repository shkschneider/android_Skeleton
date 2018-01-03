package me.shkschneider.skeleton.java

import java.security.SecureRandom

object EnumHelper {

    // <http://stackoverflow.com/a/14257525>
    fun <T : Enum<Any>> random(cls: Class<T>): T {
        return cls.enumConstants[SecureRandom().nextInt(cls.enumConstants.size)]
    }

}
