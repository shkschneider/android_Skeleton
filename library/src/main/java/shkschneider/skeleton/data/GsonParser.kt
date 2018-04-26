package me.shkschneider.skeleton.data

import android.text.TextUtils
import com.google.gson.*
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.java.StringHelper
import java.util.*
import java.util.regex.Pattern

object GsonParser : IParser<JsonObject, JsonArray> {

    override fun parse(string: String): JsonObject? {
        return try {
            Gson().fromJson(string, JsonObject::class.java)
        } catch (e: JsonParseException) {
            Logger.wtf(e)
            null
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

    fun get(jsonObject: JsonObject, string: String): JsonElement? {
        // bad formats
        if (StringHelper.count(string, "\\{") != StringHelper.count(string, "\\}")) {
            Logger.error("Bad {} format: $string")
            return null
        }
        if (StringHelper.count(string, "\\[") != StringHelper.count(string, "\\]")) {
            Logger.error("Bad [] format: $string")
            return null
        }
        if (! string.matches("^([\\{|\\[][^\\{|\\[|\\}|\\]]+(:[0-9]+)?[\\}|\\]])+$".toRegex())) {
            Logger.error("Bad format: $string")
            return null
        }
        // tags and paths
        val matcherTags = Pattern.compile("([\\{|\\[])").matcher(string)
        val tags = ArrayList<String>()
        while (matcherTags.find()) {
            tags.add(matcherTags.group())
        }
        Logger.verbose("Tags: " + tags.toString())
        val matcherPaths = Pattern.compile("[\\{|\\[]([^\\{|\\[|\\}|\\]]+)[\\}|\\]]").matcher(string)
        val paths = ArrayList<String>()
        while (matcherPaths.find()) {
            paths.add(matcherPaths.group(1))
        }
        Logger.verbose("Paths: " + paths.toString())
        // loop
        var obj = jsonObject
        for (i in tags.indices) {
            // b triggers the end
            val b = i == tags.size - 1
            val t = tags[i]
            val p = paths[i]
            // forced end
            if (obj.isJsonNull) {
                Logger.warning("Reached a JsonNull")
                return null
            }
            // object
            if (t == "{") {
                if (! obj.has(p)) {
                    Logger.error("No such object path: $p")
                    return null
                }
                val jsonElement = obj.get(p)
                // found the result
                if (b) {
                    return obj.get(p)
                }
                // keep going so check the type
                if (! jsonElement.isJsonObject) {
                    Logger.error("Invalid type (not JsonObject): $p")
                    return null
                }
                // prepare next loop
                obj = obj.getAsJsonObject(p)
            } else if (t == "[") {
                val d = p.split(":".toRegex())
                // without index
                if (d.size == 1) {
                    val s = d[0]
                    if (! obj.has(s)) {
                        Logger.error("No such array path: $p")
                        return null
                    }
                    // has to be an array
                    if (! obj.isJsonArray) {
                        Logger.error("Invalid type (not JsonArray): $p")
                        return null
                    }
                    // returns as nothing more can be done
                    return obj.getAsJsonArray(s)
                } else if (d.size == 2) {
                    val s = d[0]
                    if (! obj.has(s)) {
                        Logger.error("No such array path: $p")
                        return null
                    }
                    if (! TextUtils.isDigitsOnly(d[1])) {
                        Logger.error("Invalid index: " + d[1])
                        return null
                    }
                    val n = Integer.valueOf(d[1])
                    if (n >= d.size) {
                        Logger.error("Invalid index: $n")
                        return null
                    }
                    val jsonElement = obj.get(s)
                    val array = jsonElement.asJsonArray
                    // found the result
                    if (b) {
                        return array.get(n)
                    }
                    // keep going so check the type
                    if (! jsonElement.isJsonArray) {
                        Logger.error("Invalid type (not JsonArray): $s")
                        return null
                    }
                    // prepare next loop
                    obj = array.get(n).asJsonObject
                } else {
                    Logger.error("Bad format: $p")
                    return null
                }
            }
        }
        return obj
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
