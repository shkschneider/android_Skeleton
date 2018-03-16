package me.shkschneider.skeleton.extensions

fun Int.has(other: Int): Boolean {
    return (this and other) == 0
}
fun Long.has(other: Long): Boolean {
    return (this and other) == 0.toLong()
}

fun Number.zero(): Boolean {
    return this == 0
}

fun Number.less(other: Number): Boolean {
    return when (other) {
        is Byte -> this.toByte().less(other)
        is Short -> this.toShort().less(other)
        is Int -> this.toInt().less(other)
        is Long -> this.toLong().less(other)
        is Float -> this.toFloat().less(other)
        is Double -> this.toDouble().less(other)
        else -> false
    }
}

fun Byte.less(other: Byte): Boolean = this < other
fun Byte.less(other: Short): Boolean = this < other
fun Byte.less(other: Int): Boolean = this < other
fun Byte.less(other: Long): Boolean = this < other
fun Byte.less(other: Float): Boolean = this < other
fun Byte.less(other: Double): Boolean = this < other

fun Short.less(other: Byte): Boolean = this < other
fun Short.less(other: Short): Boolean = this < other
fun Short.less(other: Int): Boolean = this < other
fun Short.less(other: Long): Boolean = this < other
fun Short.less(other: Float): Boolean = this < other
fun Short.less(other: Double): Boolean = this < other

fun Int.less(other: Byte): Boolean = this < other
fun Int.less(other: Short): Boolean = this < other
fun Int.less(other: Int): Boolean = this < other
fun Int.less(other: Long): Boolean = this < other
fun Int.less(other: Float): Boolean = this < other
fun Int.less(other: Double): Boolean = this < other

fun Long.less(other: Byte): Boolean = this < other
fun Long.less(other: Short): Boolean = this < other
fun Long.less(other: Int): Boolean = this < other
fun Long.less(other: Long): Boolean = this < other
fun Long.less(other: Float): Boolean = this < other
fun Long.less(other: Double): Boolean = this < other

fun Float.less(other: Byte): Boolean = this < other
fun Float.less(other: Short): Boolean = this < other
fun Float.less(other: Int): Boolean = this < other
fun Float.less(other: Long): Boolean = this < other
fun Float.less(other: Float): Boolean = this < other
fun Float.less(other: Double): Boolean = this < other

fun Double.less(other: Byte): Boolean = this < other
fun Double.less(other: Short): Boolean = this < other
fun Double.less(other: Int): Boolean = this < other
fun Double.less(other: Long): Boolean = this < other
fun Double.less(other: Float): Boolean = this < other
fun Double.less(other: Double): Boolean = this < other

fun Number.more(other: Number): Boolean {
    return when (other) {
        is Byte -> this.toByte().more(other)
        is Short -> this.toShort().more(other)
        is Int -> this.toInt().more(other)
        is Long -> this.toLong().more(other)
        is Float -> this.toFloat().more(other)
        is Double -> this.toDouble().more(other)
        else -> false
    }
}

fun Byte.more(other: Byte): Boolean = this > other
fun Byte.more(other: Short): Boolean = this > other
fun Byte.more(other: Int): Boolean = this > other
fun Byte.more(other: Long): Boolean = this > other
fun Byte.more(other: Float): Boolean = this > other
fun Byte.more(other: Double): Boolean = this > other

fun Short.more(other: Byte): Boolean = this > other
fun Short.more(other: Short): Boolean = this > other
fun Short.more(other: Int): Boolean = this > other
fun Short.more(other: Long): Boolean = this > other
fun Short.more(other: Float): Boolean = this > other
fun Short.more(other: Double): Boolean = this > other

fun Int.more(other: Byte): Boolean = this > other
fun Int.more(other: Short): Boolean = this > other
fun Int.more(other: Int): Boolean = this > other
fun Int.more(other: Long): Boolean = this > other
fun Int.more(other: Float): Boolean = this > other
fun Int.more(other: Double): Boolean = this > other

fun Long.more(other: Byte): Boolean = this > other
fun Long.more(other: Short): Boolean = this > other
fun Long.more(other: Int): Boolean = this > other
fun Long.more(other: Long): Boolean = this > other
fun Long.more(other: Float): Boolean = this > other
fun Long.more(other: Double): Boolean = this > other

fun Float.more(other: Byte): Boolean = this > other
fun Float.more(other: Short): Boolean = this > other
fun Float.more(other: Int): Boolean = this > other
fun Float.more(other: Long): Boolean = this > other
fun Float.more(other: Float): Boolean = this > other
fun Float.more(other: Double): Boolean = this > other

fun Double.more(other: Byte): Boolean = this > other
fun Double.more(other: Short): Boolean = this > other
fun Double.more(other: Int): Boolean = this > other
fun Double.more(other: Long): Boolean = this > other
fun Double.more(other: Float): Boolean = this > other
fun Double.more(other: Double): Boolean = this > other
