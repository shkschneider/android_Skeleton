package me.shkschneider.skeleton.android.core.json

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import me.shkschneider.skeleton.android.log.Logger

object GsonParser : IParser<JsonObject, JsonArray> {

    override fun parse(string: String): JsonObject? =
        parse(string, Gson())

    fun parse(string: String, gson: Gson): JsonObject? =
        try {
            gson.fromJson(string, JsonObject::class.java)
        } catch (e: JsonParseException) {
            Logger.wtf(e)
            null
        }

    override fun keys(jsonObject: JsonObject): List<String> {
        val keys = ArrayList<String>()
        for ((key) in jsonObject.entrySet()) {
            keys.add(key)
        }
        return keys
    }

    override fun values(jsonObject: JsonObject): List<JsonElement> =
        jsonObject.entrySet().map { entry -> entry.value }

    override fun has(jsonObject: JsonObject, key: String): Boolean =
        jsonObject.has(key)

    // from Object

    private fun element(jsonObject: JsonObject, key: String): JsonElement? =
        if (jsonObject.has(key)) jsonObject.get(key) else null

    override fun get(jsonObject: JsonObject, key: String, fallback: JsonObject?): JsonObject? =
        element(jsonObject, key)?.asJsonObject

    override fun array(jsonObject: JsonObject, key: String, fallback: JsonArray?): JsonArray? =
        element(jsonObject, key)?.asJsonArray

    override fun string(jsonObject: JsonObject, key: String, fallback: String?): String? =
        element(jsonObject, key)?.asString ?: fallback

    override fun number(jsonObject: JsonObject, key: String, fallback: Number?): Number? =
        element(jsonObject, key)?.asNumber ?: fallback

    override fun bool(jsonObject: JsonObject, key: String, fallback: Boolean?): Boolean? =
        element(jsonObject, key)?.asBoolean ?: fallback

    // from Array

    private fun element(jsonArray: JsonArray, index: Int): JsonElement? =
        jsonArray.get(index) ?: null

    override fun get(jsonArray: JsonArray, index: Int, fallback: JsonObject?): JsonObject? =
        element(jsonArray, index)?.asJsonObject ?: fallback

    override fun array(jsonArray: JsonArray, index: Int, fallback: JsonArray?): JsonArray? =
        element(jsonArray, index)?.asJsonArray ?: fallback

    override fun string(jsonArray: JsonArray, index: Int, fallback: String?): String? =
        element(jsonArray, index)?.asString ?: fallback

    override fun number(jsonArray: JsonArray, index: Int, fallback: Number?): Number? =
        element(jsonArray, index)?.asNumber ?: fallback

    override fun bool(jsonArray: JsonArray, index: Int, fallback: Boolean?): Boolean? =
        element(jsonArray, index)?.asBoolean ?: fallback

}
