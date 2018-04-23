package me.shkschneider.skeleton.extensions

// Avoids nulls to be translated to "null"
fun Any?.toStringOrEmpty(): String {
    return this?.toString().orEmpty()
}
