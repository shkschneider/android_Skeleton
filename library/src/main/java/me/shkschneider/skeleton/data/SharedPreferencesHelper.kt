package me.shkschneider.skeleton.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.Logger

@SuppressLint("CommitPrefEdits")
object SharedPreferencesHelper {

    private fun get(name: String?): SharedPreferences {
        return ContextHelper.applicationContext().getSharedPreferences(
                if (name.isNullOrBlank()) ApplicationHelper.packageName() else name,
                Context.MODE_PRIVATE)
    }

    fun contains(key: String): Boolean {
        return get(null).contains(key)
    }

    fun contains(key: String, name: String? = null): Boolean {
        return get(name).contains(key)
    }

    fun remove(key: String, name: String? = null) {
        get(name).edit {
            remove(key)
        }
    }

    fun clear(name: String? = null) {
        get(name).edit {
            clear()
        }
    }

    // String

    fun putString(key: String, value: String, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putString(key, value)
        }
    }

    fun getString(key: String, defaultValue: String, name: String? = null): String? {
        return get(name).getString(key, defaultValue)
    }

    // Set<String>

    fun putStringSet(key: String, value: Set<String>, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putStringSet(key, value)
        }
    }

    fun getStringSet(key: String, defaultValue: Set<String>, name: String? = null): Set<String>? {
        return get(name).getStringSet(key, defaultValue)
    }

    // Integer

    fun putInteger(key: String, value: Int, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putInt(key, value)
        }
    }

    fun getInteger(key: String, defaultValue: Int, name: String? = null): Int? {
        return get(name).getInt(key, defaultValue)
    }

    // Long

    fun putLong(key: String, value: Long, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putLong(key, value)
        }
    }

    fun getLong(key: String, defaultValue: Long, name: String? = null): Long? {
        return get(name).getLong(key, defaultValue)
    }

    // Float

    fun putFloat(key: String, value: Float, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putFloat(key, value)
        }
    }

    fun getFloat(key: String, defaultValue: Float, name: String? = null): Float? {
        return get(name).getFloat(key, defaultValue)
    }

    // Boolean

    fun putBoolean(key: String, value: Boolean, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        get(name).edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean, name: String? = null): Boolean? {
        return get(name).getBoolean(key, defaultValue)
    }

}
