package me.shkschneider.skeleton.kotlin.jvm

internal object UninitializedValue

class Once<T>(private val notifier: (() -> Unit)? = null) {

    private var _value: Any? = null
        private set(value) {
            if (field == UninitializedValue) {
                field = value
                notifier?.invoke()
            } else {
                throw IllegalStateException("Could only be set once")
            }
        }

    fun get(): T? {
        @Suppress("UNCHECKED_CAST")
        return _value as T
    }

    @Throws(IllegalStateException::class)
    fun set(value: T?) {
        this._value = value
    }

    fun reset() {
        this._value = UninitializedValue
    }

    val initialized: Boolean
        get() = _value !== UninitializedValue

    override fun toString() = if (initialized) _value.toString() else "Once value not initialized yet."

}

fun <T> once() = Once<T>()
fun <T> once(notifier: (() -> Unit)) = Once<T>(notifier)
