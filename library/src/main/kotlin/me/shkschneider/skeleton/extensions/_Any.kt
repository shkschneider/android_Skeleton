package me.shkschneider.skeleton.extensions

// FIXME TODO == null + != null

// = ?: x FIXME
fun <T:Any> T?.orElse(any: T) : Any {
    return this ?: any
}

fun <T:Any> T?.isNull(): Boolean {
    return (this == null)
}

fun <T:Any> T?.isNotNull(): Boolean {
    return (this != null)
}

fun <T:Any?> T?.toStringOrEmpty(): String {
    if (this === null) {
        return ""
    } else {
        return this.toString()
    }
}
