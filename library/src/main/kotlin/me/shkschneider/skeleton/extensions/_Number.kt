package me.shkschneider.skeleton.extensions

// Flags

fun Int.has(other: Int): Boolean {
    return (this and other) != 0
}

fun Long.has(other: Long): Boolean {
    return (this and other) != 0.toLong()
}
