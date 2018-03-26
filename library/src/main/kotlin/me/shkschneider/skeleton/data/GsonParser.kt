package me.shkschneider.skeleton.data

import android.text.TextUtils
import com.google.gson.*
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.java.StringHelper
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

object GsonParser {

    fun parse(string: String): JsonObject? {
        try {
            return Gson().fromJson(string, JsonObject::class.java)
        } catch (e: JsonParseException) {
            Logger.wtf(e)
            return null
        }
    }

    fun parse(inputStream: InputStream): JsonObject? {
        val string = FileHelper.readString(inputStream)
        if (string.isNullOrEmpty()) {
            Logger.warning("String was NULL")
            return null
        }
        return parse(string!!)
    }

    fun get(jsonObject: JsonObject, string: String): JsonElement? {
        // bad formats
        if (StringHelper.count(string, "\\{") != StringHelper.count(string, "\\}")) {
            Logger.error("Bad {} format: " + string)
            return null
        }
        if (StringHelper.count(string, "\\[") != StringHelper.count(string, "\\]")) {
            Logger.error("Bad [] format: " + string)
            return null
        }
        if (! string.matches("^([\\{|\\[][^\\{|\\[|\\}|\\]]+(:[0-9]+)?[\\}|\\]])+$".toRegex())) {
            Logger.error("Bad format: " + string)
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
                    Logger.error("No such object path: " + p)
                    return null
                }
                val jsonElement = obj.get(p)
                // found the result
                if (b) {
                    return obj.get(p)
                }
                // keep going so check the type
                if (! jsonElement.isJsonObject) {
                    Logger.error("Invalid type (not JsonObject): " + p)
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
                        Logger.error("No such array path: " + p)
                        return null
                    }
                    // has to be an array
                    if (! obj.isJsonArray) {
                        Logger.error("Invalid type (not JsonArray): " + p)
                        return null
                    }
                    // returns as nothing more can be done
                    return obj.getAsJsonArray(s)
                } else if (d.size == 2) {
                    val s = d[0]
                    if (! obj.has(s)) {
                        Logger.error("No such array path: " + p)
                        return null
                    }
                    if (! TextUtils.isDigitsOnly(d[1])) {
                        Logger.error("Invalid index: " + d[1])
                        return null
                    }
                    val n = Integer.valueOf(d[1])!!
                    if (n >= d.size) {
                        Logger.error("Invalid index: " + n)
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
                        Logger.error("Invalid type (not JsonArray): " + s)
                        return null
                    }
                    // prepare next loop
                    obj = array.get(n).asJsonObject
                } else {
                    Logger.error("Bad format: " + p)
                    return null
                }
            }
        }
        return obj
    }

    fun element(jsonObject: JsonObject, key: String): JsonElement? {
        return jsonObject.get(key)
    }

    fun keys(jsonObject: JsonObject): List<String> {
        val keys = ArrayList<String>()
        for ((key) in jsonObject.entrySet()) {
            keys.add(key)
        }
        return keys
    }

    fun values(jsonObject: JsonObject): List<JsonElement> {
        val values = ArrayList<JsonElement>()
        for ((_, value) in jsonObject.entrySet()) {
            values.add(value)
        }
        return values
    }

    fun has(jsonObject: JsonObject, key: String): Boolean? {
        return jsonObject.has(key)
    }

    fun copy(jsonObject: JsonObject, key: String): JsonObject? {
        val jsonElement = element(jsonObject, key)
        return if (jsonElement != null && jsonElement is JsonObject) {
            jsonElement.asJsonObject
        } else null
    }

    fun array(jsonObject: JsonObject, key: String): JsonArray? {
        val jsonElement = element(jsonObject, key)
        return if (jsonElement != null && jsonElement is JsonArray) {
            jsonElement.asJsonArray
        } else null
    }

    fun string(jsonObject: JsonObject, key: String, fallback: String? = null): String? {
        val jsonElement = jsonObject.get(key)
        return if (jsonElement != null && jsonElement !is JsonNull) {
            jsonElement.asString
        } else fallback
    }

    // NOT @Nullable
    fun number(jsonObject: JsonObject, key: String, fallback: Number): Number {
        val jsonElement = jsonObject.get(key)
        return if (jsonElement != null && jsonElement !is JsonNull) {
            jsonElement.asNumber
        } else fallback
    }

    fun number(jsonObject: JsonObject, key: String): Number? {
        return number(jsonObject, key, 0)
    }

    fun bool(jsonObject: JsonObject, key: String, fallback: Boolean? = null): Boolean? {
        val jsonElement = jsonObject.get(key)
        return if (jsonElement != null && jsonElement !is JsonNull) {
            jsonElement.asBoolean
        } else fallback
    }

}