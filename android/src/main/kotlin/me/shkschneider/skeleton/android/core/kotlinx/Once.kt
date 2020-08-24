package me.shkschneider.skeleton.android.core.kotlinx

internal object UninitializedObject

class Once<T>(private val notifier: (() -> Unit)? = null) {

    private var _value: Any? = null
        private set(value) {
            if (field == UninitializedObject) {
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
        this._value = UninitializedObject
    }

    val initialized: Boolean
        get() = _value !== UninitializedObject

    override fun toString() = if (initialized) _value.toString() else "Once value not initialized yet."

}

fun <T> once() = Once<T>()
fun <T> once(notifier: (() -> Unit)) = Once<T>(notifier)

object Test {

    private val once1 = once<String>()
    private val once2 = once<String> { callback() }
    private val once3 = once<String> { callback() }

    fun test() {
        // once.value = "test1" // private set
        once1.set("test2")
        once1.get()
        once1.set("test3") // IllegalStateException
        once1.get()

        once2.set("test4")
        once2.get()
        once2.reset()
        once2.set("test5")
        once2.get()
        once2.set("test6") // IllegalStateException
        once2.get()
    }

    fun callback() {
        "notified: ${once2.get()}"
    }

}
