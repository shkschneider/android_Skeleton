package me.shkschneider.skeleton.java

import com.google.gson.Gson

object ObjectHelper {

    @Deprecated("Broken (with Koltin).", ReplaceWith("Gson.toJson()", "com.google.gson.Gson"))
    fun jsonify(obj: Any?): String {
        obj ?: return "{}"
        return Gson().toJson(obj, obj::class.java)
    }

}
