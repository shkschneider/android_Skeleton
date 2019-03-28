package me.shkschneider.skeleton.extensions

// enumValues<E>()

inline fun <reified E : Enum<E>> enumFromName(name: String, default: E? = null) =
        enumValues<E>().find { it.name == name } ?: default

inline fun <reified E : Enum<E>> enumFromOrdinal(ordinal: Int, default: E? = null) =
        enumValues<E>().find { it.ordinal == ordinal } ?: default

inline fun <reified E : Enum<E>> enumWithCondition(condition: (E) -> Boolean, default: E? = null) =
        enumValues<E>().find(condition) ?: default
