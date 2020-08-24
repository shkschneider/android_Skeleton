package me.shkschneider.skeleton.kotlin

import java.util.Locale

// <https://github.com/tailwhiper/kotlin-enum-extensions>

inline fun <reified E : Enum<E>> valueOf(name: String, default: E) =
        enumValues<E>().find { it.name == name } ?: default

inline fun <reified E : Enum<E>> valueOf(ordinal: Int, default: E) =
        enumValues<E>().find { it.ordinal == ordinal } ?: default

inline fun <reified E : Enum<E>> valueOfWithCondition(condition: (E) -> Boolean) =
        enumValues<E>().find(condition)

inline fun <reified E : Enum<E>> valueOfWithCondition(condition: (E) -> Boolean, default: E) =
        enumValues<E>().find(condition) ?: default

inline fun <reified E : Enum<E>> valueOfIgnoreCase(name: String) =
        enumValues<E>().find { it.name.toUpperCase(Locale.getDefault()) == name.toUpperCase(Locale.getDefault()) }
