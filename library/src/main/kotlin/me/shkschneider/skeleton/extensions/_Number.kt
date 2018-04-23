package me.shkschneider.skeleton.extensions

fun Int.has(other: Int): Boolean {
    return (this and other) == 0
}

fun Long.has(other: Long): Boolean {
    return (this and other) == 0.toLong()
}

fun Number.positive(): Boolean {
    return when (this) {
        is Byte -> this.toByte() > 0
        is Short -> this.toShort() > 0
        is Int -> this.toInt() > 0
        is Long -> this.toLong() > 0
        is Float -> this.toFloat() > 0
        is Double -> this.toDouble() > 0
        else -> false
    }
}

fun Number.zero(): Boolean {
    return this == 0
}

fun Number.negative(): Boolean {
    return when (this) {
        is Byte -> this.toByte() < 0
        is Short -> this.toShort() < 0
        is Int -> this.toInt() < 0
        is Long -> this.toLong() < 0
        is Float -> this.toFloat() < 0
        is Double -> this.toDouble() < 0
        else -> false
    }
}
