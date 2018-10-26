package me.shkschneider.skeleton.datax

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.securityx.ICrypt
import kotlin.reflect.KClass

class Keystore(
        private val store: String,
        private val crypt: ICrypt<String>? = null
) {

    private val gson: Gson by lazy {
        Gson()
    }

    private fun editor(): SharedPreferences {
        return ContextHelper.applicationContext().getSharedPreferences(store, Context.MODE_PRIVATE)
    }

    fun contains(key: String): Boolean {
        return editor().contains(key)
    }

    fun <T:Any> get(key: String, klass: KClass<T>, default: T): T {
        return get(key, klass) ?: default
    }

    fun <T:Any> get(key: String, klass: KClass<T>): T? {
        return editor().getString(key, null)?.run {
            return crypt?.let { crypt ->
                try {
                    return gson.fromJson(crypt.decrypt(this), klass.java)
                } catch (e: JsonSyntaxException) {
                    Logger.wtf(e)
                    return null
                }
            } ?: run {
                return gson.fromJson(this, klass.java)
            }
        }
    }

    fun <T:Any> put(key: String, data: T) {
        return editor().edit {
            val json = gson.toJson(data)
            crypt?.let { crypt ->
                putString(key, crypt.encrypt(json))
            } ?: run {
                putString(key, json)
            }
        }
    }

}