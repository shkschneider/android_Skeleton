package me.shkschneider.skeleton.java

import me.shkschneider.skeleton.helperx.Logger

@Deprecated("Reflection allows programmatic access to information about the methods of loaded classes.")
object ReflectHelper {

    object Field {

        fun fields(obj: Any): List<String>? {
            return fields(obj::class.java)
        }

        fun fields(cls: Class<Any>): List<String>? {
            try {
                return cls.declaredFields.map { it.name }
            } catch (e: Exception) {
                Logger.wtf(e)
            }
            return null
        }

        fun field(obj: Any, declaredField: String): Any? {
            return field(obj::class.java, declaredField)
        }

        fun field(cls: Class<Any>, declaredField: String): Any? {
            try {
                val field = cls.getDeclaredField(declaredField)
                field.isAccessible = true
                return field.get(cls)
            } catch (e: Exception) {
                Logger.wtf(e)
            }
            return null
        }

        fun field(obj: Any, declaredField: String, value: Any): Boolean {
            return field(obj::class.java, declaredField, value)
        }

        fun field(cls: Class<Any>, declaredField: String, value: Any): Boolean {
            try {
                val field = cls.getDeclaredField(declaredField)
                field.isAccessible = true
                field.set(cls, value)
                return true
            } catch (e: Exception) {
                Logger.wtf(e)
            }
            return false
        }

    }

    object Method {

        private val EMPTY_SIGNATURE = arrayOf<Class<*>>()

        fun methods(obj: Any): List<String>? {
            return methods(obj::class.java)
        }

        fun method(obj: Any, declaredMethod: String, signature: Array<Class<*>>): Any? {
            return method(obj::class.java, declaredMethod, signature, EMPTY_SIGNATURE)
        }

        fun method(obj: Any, declaredMethod: String, signature: Array<Class<*>>, vararg params: Any): Any? {
            return method(obj::class.java, declaredMethod, signature, *params)
        }

        fun methods(cls: Class<*>): List<String>? {
            try {
                return cls.declaredMethods.map { it.name }
            } catch (e: Exception) {
                Logger.wtf(e)
            }
            return null
        }

        fun method(cls: Class<*>, declaredMethod: String, signature: Array<Class<*>>, vararg params: Any = EMPTY_SIGNATURE): Any? {
            try {
                val method = cls.getDeclaredMethod(declaredMethod, *signature)
                method.isAccessible = true
                return method.invoke(cls, *params)
            } catch (e: Exception) {
                Logger.wtf(e)
            }
            return null
        }

    }

}
