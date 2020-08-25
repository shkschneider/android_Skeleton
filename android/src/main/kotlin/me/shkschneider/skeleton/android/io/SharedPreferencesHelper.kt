package me.shkschneider.skeleton.android.io

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.log.Logger

// TODO encryption
object SharedPreferencesHelper {

    private fun editor(name: String? = null): SharedPreferences =
        ContextProvider.applicationContext().getSharedPreferences(
            if (name.isNullOrBlank()) ApplicationHelper.packageName else name,
            Context.MODE_PRIVATE
        )

    fun contains(key: String): Boolean =
        editor(null).contains(key)

    fun contains(key: String, name: String? = null): Boolean =
        editor(name).contains(key)

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

    fun getString(key: String, defaultValue: String, name: String? = null): String? =
        editor(name).getString(key, defaultValue)

    // Set<String>

    fun putStringSet(key: String, value: Set<String>, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putStringSet(key, value)
        }
    }

    fun getStringSet(key: String, defaultValue: Set<String>, name: String? = null): Set<String>? =
        editor(name).getStringSet(key, defaultValue)

    // Integer

    fun putInteger(key: String, value: Int, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putInt(key, value)
        }
    }

    fun getInteger(key: String, defaultValue: Int, name: String? = null): Int? =
        editor(name).getInt(key, defaultValue)

    // Long

    fun putLong(key: String, value: Long, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putLong(key, value)
        }
    }

    fun getLong(key: String, defaultValue: Long, name: String? = null): Long? =
        editor(name).getLong(key, defaultValue)

    // Float

    fun putFloat(key: String, value: Float, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putFloat(key, value)
        }
    }

    fun getFloat(key: String, defaultValue: Float, name: String? = null): Float? =
        editor(name).getFloat(key, defaultValue)

    // Boolean

    fun putBoolean(key: String, value: Boolean, name: String? = null) {
        Logger.verbose("$name: $key = $value")
        editor(name).edit {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean, name: String? = null): Boolean? =
        editor(name).getBoolean(key, defaultValue)

}
