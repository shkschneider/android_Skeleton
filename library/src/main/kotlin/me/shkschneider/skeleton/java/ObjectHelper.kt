package me.shkschneider.skeleton.java

import com.google.gson.Gson

object ObjectHelper {

    fun jsonify(obj: Any?): String {
        obj ?: return "{}"
        return Gson().toJson(obj, obj::class.java)
    }

}
