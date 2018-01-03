package me.shkschneider.skeleton.data

import android.text.TextUtils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.InputStream
import java.util.ArrayList

import me.shkschneider.skeleton.helper.LogHelper

object JsonParser {

    fun parse(string: String): JSONObject? {
        try {
            return JSONObject(string)
        } catch (e: JSONException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun parse(inputStream: InputStream): JSONObject? {
        val string = FileHelper.readString(inputStream)
        if (TextUtils.isEmpty(string)) {
            LogHelper.warning("String was NULL")
            return null
        }
        return parse(string!!)
    }

    fun <T> get(jsonObject: JSONObject, key: String, fallback: T): T? {
        try {
            @Suppress("UNCHECKED_CAST")
            return jsonObject.get(key) as T
        } catch (e: JSONException) {
            return fallback
        }
    }

    fun <T> get(jsonArray: JSONArray, index: Int, fallback: T): T? {
        try {
            @Suppress("UNCHECKED_CAST")
            return jsonArray.get(index) as T
        } catch (e: JSONException) {
            return fallback
        }
    }

    fun keys(jsonObject: JSONObject): List<String> {
        val keys = ArrayList<String>()
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            keys.add(iterator.next())
        }
        return keys
    }

    fun values(jsonObject: JSONObject): List<Any> {
        val values = ArrayList<Any>()
        try {
            keys(jsonObject).mapTo(values) { jsonObject.get(it) }
        } catch (e: JSONException) {
            LogHelper.wtf(e)
        }
        return values
    }

    fun has(jsonObject: JSONObject, key: String): Boolean {
        return jsonObject.has(key)
    }

}
