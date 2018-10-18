package me.shkschneider.skeleton.datax

import com.google.gson.*
import me.shkschneider.skeleton.helperx.Logger
import java.util.*

object GsonParser : IParser<JsonObject, JsonArray> {

    override fun parse(string: String): JsonObject? {
        return parse(string, Gson())
    }

    fun parse(string: String, gson: Gson): JsonObject? {
        try {
            return gson.fromJson(string, JsonObject::class.java)
        } catch (e: JsonParseException) {
            Logger.wtf(e)
            return null
        }
    }

    override fun keys(jsonObject: JsonObject): List<String> {
        val keys = ArrayList<String>()
        for ((key) in jsonObject.entrySet()) {
            keys.add(key)
        }
        return keys
    }

    override fun values(jsonObject: JsonObject): List<JsonElement> {
        return jsonObject.entrySet().map { entry -> entry.value }
    }

    override fun has(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.has(key)
    }

    // from Object

    private fun element(jsonObject: JsonObject, key: String): JsonElement? {
        if (jsonObject.has(key)) {
            return jsonObject.get(key)
        }
        return null
    }

    override fun get(jsonObject: JsonObject, key: String, fallback: JsonObject?): JsonObject? {
        return element(jsonObject, key)?.asJsonObject
    }

    override fun array(jsonObject: JsonObject, key: String, fallback: JsonArray?): JsonArray? {
        return element(jsonObject, key)?.asJsonArray
    }

    override fun string(jsonObject: JsonObject, key: String, fallback: String?): String? {
        return element(jsonObject, key)?.asString ?: fallback
    }

    override fun number(jsonObject: JsonObject, key: String, fallback: Number?): Number? {
        return element(jsonObject, key)?.asNumber ?: fallback
    }

    override fun bool(jsonObject: JsonObject, key: String, fallback: Boolean?): Boolean? {
        return element(jsonObject, key)?.asBoolean ?: fallback
    }

    // from Array

    private fun element(jsonArray: JsonArray, index: Int): JsonElement? {
        return jsonArray.get(index) ?: null
    }

    override fun get(jsonArray: JsonArray, index: Int, fallback: JsonObject?): JsonObject? {
        return element(jsonArray, index)?.asJsonObject ?: fallback
    }

    override fun array(jsonArray: JsonArray, index: Int, fallback: JsonArray?): JsonArray? {
        return element(jsonArray, index)?.asJsonArray ?: fallback
    }

    override fun string(jsonArray: JsonArray, index: Int, fallback: String?): String? {
        return element(jsonArray, index)?.asString ?: fallback
    }

    override fun number(jsonArray: JsonArray, index: Int, fallback: Number?): Number? {
        return element(jsonArray, index)?.asNumber ?: fallback
    }

    override fun bool(jsonArray: JsonArray, index: Int, fallback: Boolean?): Boolean? {
        return element(jsonArray, index)?.asBoolean ?: fallback
    }

}
