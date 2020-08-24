package me.shkschneider.skeleton.android.core.datax

interface IParser<Object, Array> {

    fun parse(string: String): Object?

    fun keys(jsonObject: Object): List<String>

    fun values(jsonObject: Object): List<Any>

    fun has(jsonObject: Object, key: String): Boolean

    // from Object

    fun get(jsonObject: Object, key: String, fallback: Object? = null): Object?

    fun array(jsonObject: Object, key: String, fallback: Array? = null): Array?

    fun string(jsonObject: Object, key: String, fallback: String? = null): String?

    fun number(jsonObject: Object, key: String, fallback: Number? = null): Number?

    fun bool(jsonObject: Object, key: String, fallback: Boolean? = null): Boolean?

    // from Array

    fun get(jsonArray: Array, index: Int, fallback: Object? = null): Object?

    fun array(jsonArray: Array, index: Int, fallback: Array? = null): Array?

    fun string(jsonArray: Array, index: Int, fallback: String? = null): String?

    fun number(jsonArray: Array, index: Int, fallback: Number? = null): Number?

    fun bool(jsonArray: Array, index: Int, fallback: Boolean? = null): Boolean?

}