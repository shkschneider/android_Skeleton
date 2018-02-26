package me.shkschneider.skeleton.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.SharedPreferencesCompat
import android.text.TextUtils

import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.LogHelper

@SuppressLint("CommitPrefEdits")
object SharedPreferencesHelper {

    private fun get(name: String?): SharedPreferences {
        return ContextHelper.applicationContext().getSharedPreferences(
                if (TextUtils.isEmpty(name)) ApplicationHelper.packageName() else name,
                Context.MODE_PRIVATE)
    }

    fun contains(key: String): Boolean {
        return get(null).contains(key)
    }

    fun contains(name: String, key: String): Boolean {
        return get(name).contains(key)
    }

    fun remove(key: String) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().remove(key))
    }

    fun remove(name: String, key: String) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().remove(key))
    }

    fun clear() {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().clear())
    }

    fun clear(name: String) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().clear())
    }

    // String

    fun putString(key: String, value: String) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putString(key, value))
    }

    fun putString(name: String, key: String, value: String) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putString(key, value))
    }

    fun getString(key: String, defaultValue: String): String? {
        return get(null).getString(key, defaultValue)
    }

    fun getString(name: String, key: String, defaultValue: String): String? {
        return get(name).getString(key, defaultValue)
    }

    // Set<String>

    fun putStringSet(key: String, value: Set<String>) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putStringSet(key, value))
    }

    fun putStringSet(name: String, key: String, value: Set<String>) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putStringSet(key, value))
    }

    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        return get(null).getStringSet(key, defaultValue)
    }

    fun getStringSet(name: String, key: String, defaultValue: Set<String>): Set<String>? {
        return get(name).getStringSet(key, defaultValue)
    }

    // Integer

    fun putInteger(key: String, value: Int?) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putInt(key, value!!))
    }

    fun putInteger(name: String, key: String, value: Int?) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putInt(key, value!!))
    }

    fun getInteger(key: String, defaultValue: Int): Int? {
        return get(null).getInt(key, defaultValue)
    }

    fun getInteger(name: String, key: String, defaultValue: Int): Int? {
        return get(name).getInt(key, defaultValue)
    }

    // Long

    fun putLong(key: String, value: Long?) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putLong(key, value!!))
    }

    fun putLong(name: String, key: String, value: Long?) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putLong(key, value!!))
    }

    fun getLong(key: String, defaultValue: Long): Long? {
        return get(null).getLong(key, defaultValue)
    }

    fun getLong(name: String, key: String, defaultValue: Long): Long? {
        return get(name).getLong(key, defaultValue)
    }

    // Float

    fun putFloat(key: String, value: Float?) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putFloat(key, value!!))
    }

    fun putFloat(name: String, key: String, value: Float?) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putFloat(key, value!!))
    }

    fun getFloat(key: String, defaultValue: Float): Float? {
        return get(null).getFloat(key, defaultValue)
    }

    fun getFloat(name: String, key: String, defaultValue: Float): Float? {
        return get(name).getFloat(key, defaultValue)
    }

    // Boolean

    fun putBoolean(key: String, value: Boolean) {
        LogHelper.verbose("$key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(null).edit().putBoolean(key, value))
    }

    fun putBoolean(name: String, key: String, value: Boolean) {
        LogHelper.verbose("$name: $key = $value")
        SharedPreferencesCompat.EditorCompat.getInstance().apply(get(name).edit().putBoolean(key, value))
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean? {
        return get(null).getBoolean(key, defaultValue)
    }

    fun getBoolean(name: String, key: String, defaultValue: Boolean): Boolean? {
        return get(name).getBoolean(key, defaultValue)
    }

}
