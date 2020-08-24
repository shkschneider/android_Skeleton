package me.shkschneider.skeleton.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.log.Logger

object SharedPreferencesHelper {

    private fun editor(name: String?): SharedPreferences {
        return ContextHelper.applicationContext().getSharedPreferences(
                if (name.isNullOrBlank()) ApplicationHelper.packageName else name,
                Context.MODE_PRIVATE)
    }

    fun contains(key: String): Boolean {
        return editor(null).contains(key)
    }

    fun contains(key: String, name: String? = null): Boolean {
        return editor(name).contains(key)
    }

    fun remove(key: String, name: String? = null) {
        editor(name).edit {
            remove(key)
        }
    }

    fun clear(name: String? = null) {
        editor(name).edit {
            clear()
        }
    }

    // String

    fun putString(key: String, value: String, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putString(key, value)
        }
    }

    fun getString(key: String, defaultValue: String, name: String? = null): String? {
        return editor(name).getString(key, defaultValue)
    }

    // Set<String>

    fun putStringSet(key: String, value: Set<String>, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putStringSet(key, value)
        }
    }

    fun getStringSet(key: String, defaultValue: Set<String>, name: String? = null): Set<String>? {
        return editor(name).getStringSet(key, defaultValue)
    }

    // Integer

    fun putInteger(key: String, value: Int, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putInt(key, value)
        }
    }

    fun getInteger(key: String, defaultValue: Int, name: String? = null): Int? {
        return editor(name).getInt(key, defaultValue)
    }

    // Long

    fun putLong(key: String, value: Long, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putLong(key, value)
        }
    }

    fun getLong(key: String, defaultValue: Long, name: String? = null): Long? {
        return editor(name).getLong(key, defaultValue)
    }

    // Float

    fun putFloat(key: String, value: Float, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putFloat(key, value)
        }
    }

    fun getFloat(key: String, defaultValue: Float, name: String? = null): Float? {
        return editor(name).getFloat(key, defaultValue)
    }

    // Boolean

    fun putBoolean(key: String, value: Boolean, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean, name: String? = null): Boolean? {
        return editor(name).getBoolean(key, defaultValue)
    }

}
