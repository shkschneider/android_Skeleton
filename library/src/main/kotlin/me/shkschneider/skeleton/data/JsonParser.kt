package me.shkschneider.skeleton.data

import me.shkschneider.skeleton.helper.Logger
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

@Deprecated("Use Gson library.")
object JsonParser : IParser<JSONObject, JSONArray> {

    override fun parse(string: String): JSONObject? {
        return try {
            JSONObject(string)
        } catch (e: JSONException) {
            Logger.wtf(e)
            null
        }
    }

    override fun keys(jsonObject: JSONObject): List<String> {
        val keys = ArrayList<String>()
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            keys.add(iterator.next())
        }
        return keys
    }

    override fun values(jsonObject: JSONObject): List<Any> {
        val values = ArrayList<Any>()
        try {
            keys(jsonObject).forEach { key ->
                values.add(jsonObject.get(key))
            }
        } catch (e: JSONException) {
            Logger.wtf(e)
        }
        return values
    }

    override fun has(jsonObject: JSONObject, key: String): Boolean {
        return jsonObject.has(key)
    }

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

    override fun get(jsonArray: JSONArray, index: Int, fallback: JSONObject?): JSONObject? {
        return try {
            jsonArray.getJSONObject(index) ?: fallback
        } catch (e: JSONException) {
            return fallback
        }
    }

    override fun array(jsonArray: JSONArray, index: Int, fallback: JSONArray?): JSONArray? {
        return try {
            jsonArray.getJSONArray(index) ?: fallback
        } catch (e: JSONException) {
            return fallback
        }
    }

    override fun string(jsonArray: JSONArray, index: Int, fallback: String?): String? {
        return try {
            jsonArray.getString(index) ?: fallback
        } catch (e: JSONException) {
            return fallback
        }
    }

    override fun number(jsonArray: JSONArray, index: Int, fallback: Number?): Number? {
        return try {
            jsonArray.get(index) as? Number? ?: fallback
        } catch (e: JSONException) {
            return fallback
        }
    }

    override fun bool(jsonArray: JSONArray, index: Int, fallback: Boolean?): Boolean? {
        return try {
            jsonArray.getBoolean(index)
        } catch (e: JSONException) {
            return fallback
        }
    }

}
