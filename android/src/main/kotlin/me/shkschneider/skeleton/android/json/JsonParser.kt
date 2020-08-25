package me.shkschneider.skeleton.android.json

import me.shkschneider.skeleton.kotlin.jvm.tryOr
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import org.json.JSONArray
import org.json.JSONObject

@Deprecated("Deprecated.", ReplaceWith("GsonParser"))
object JsonParser : IParser<JSONObject, JSONArray> {

    override fun parse(string: String): JSONObject? =
        tryOrNull {
            JSONObject(string)
        }

    override fun keys(jsonObject: JSONObject): List<String> {
        val keys = ArrayList<String>()
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            keys.add(iterator.next())
        }
        return keys
    }

    override fun values(jsonObject: JSONObject): List<Any>? =
        tryOrNull {
            ArrayList<Any>().apply {
                keys(jsonObject).forEach { key ->
                    this += jsonObject.get(key)
                }
            }
        }

    override fun has(jsonObject: JSONObject, key: String): Boolean =
        jsonObject.has(key)

    // from Object

    override fun get(jsonObject: JSONObject, key: String, fallback: JSONObject?): JSONObject? {
        if (jsonObject.has(key)) {
            return jsonObject.getJSONObject(key) ?: fallback
        }
        return fallback
    }

    override fun array(jsonObject: JSONObject, key: String, fallback: JSONArray?): JSONArray? {
        if (jsonObject.has(key)) {
            return jsonObject.getJSONArray(key) ?: fallback
        }
        return fallback
    }

    override fun string(jsonObject: JSONObject, key: String, fallback: String?): String? {
        if (jsonObject.has(key)) {
            return jsonObject.getString(key) ?: fallback
        }
        return fallback
    }

    override fun number(jsonObject: JSONObject, key: String, fallback: Number?): Number? {
        if (jsonObject.has(key)) {
            return jsonObject.get(key) as Number? ?: fallback
        }
        return null
    }

    override fun bool(jsonObject: JSONObject, key: String, fallback: Boolean?): Boolean? {
        if (jsonObject.has(key)) {
            return jsonObject.getBoolean(key)
        }
        return fallback
    }

    // from Array

    override fun get(jsonArray: JSONArray, index: Int, fallback: JSONObject?): JSONObject? =
        tryOr(fallback) {
            jsonArray.getJSONObject(index) ?: fallback
        }

    override fun array(jsonArray: JSONArray, index: Int, fallback: JSONArray?): JSONArray? =
        tryOr(fallback) {
            jsonArray.getJSONArray(index) ?: fallback
        }

    override fun string(jsonArray: JSONArray, index: Int, fallback: String?): String? =
        tryOr(fallback) {
            jsonArray.getString(index) ?: fallback
        }

    override fun number(jsonArray: JSONArray, index: Int, fallback: Number?): Number? =
        tryOr(fallback) {
            jsonArray.get(index) as? Number? ?: fallback
        }

    override fun bool(jsonArray: JSONArray, index: Int, fallback: Boolean?): Boolean? =
        tryOr(fallback) {
            jsonArray.getBoolean(index)
        }

}
