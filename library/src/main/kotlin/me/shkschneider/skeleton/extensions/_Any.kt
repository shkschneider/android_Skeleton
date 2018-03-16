package me.shkschneider.skeleton.extensions

// FIXME TODO () init {}
// FIXME TODO == null + != null
// FIXME TODO TargetApi -> RequiresApi

// = ?: x
fun <T:Any> T?.orElse(any: T) : Any {
    return this ?: any
}

fun Any?.ifNull(block: () -> Unit) {
    if (this == null) {
        block()
    }
}

fun Any?.ifNotNull(block: () -> Unit) {
    if (this == null) {
        block()
    }
}

fun <T:Any> T?.isNull(): Boolean {
    return (this == null)
}

fun <T:Any> T?.isNotNull(): Boolean {
    return (this != null)
}
