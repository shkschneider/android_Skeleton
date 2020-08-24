package me.shkschneider.skeleton

// Flags

fun Int.has(other: Int): Boolean =
        (this and other) != 0

fun Long.has(other: Long): Boolean =
        (this and other) != 0.toLong()
