package me.shkschneider.skeleton.android.security

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.kotlin.security.ICrypt
import kotlin.reflect.KClass

class Keystore(
        private val store: String,
        private val crypt: ICrypt<String>? = null
) {

    private val gson: Gson by lazy {
        Gson()
    }

    private fun editor(): SharedPreferences =
        ContextProvider.applicationContext().getSharedPreferences(store, Context.MODE_PRIVATE)

    fun contains(key: String): Boolean =
        editor().contains(key)

    fun <T : Any> get(key: String, klass: KClass<T>, default: T): T =
        get(key, klass) ?: default

    fun <T : Any> get(key: String, klass: KClass<T>): T? =
        editor().getString(key, null)?.run {
            return crypt?.let { crypt ->
                return try {
                    gson.fromJson(crypt.decrypt(this), klass.java)
                } catch (e: JsonSyntaxException) {
                    Logger.wtf(e)
                    null
                }
            } ?: run {
                return gson.fromJson(this, klass.java)
            }
        }

    fun <T : Any> put(key: String, data: T) =
        editor().edit {
            val json = gson.toJson(data)
            crypt?.let { crypt ->
                putString(key, crypt.encrypt(json))
            } ?: run {
                putString(key, json)
            }
        }

}
