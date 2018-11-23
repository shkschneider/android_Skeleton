package me.shkschneider.skeleton.extensions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// runs job in background
fun <T: Any> Any.coroutine(job: (() -> T?)) {
    GlobalScope.launch {
        job()
    }
}

// runs jobs in background then executes callback with the result
fun <T: Any> Any.coroutine(job: (() -> T?), callback: ((T?) -> Unit)? = null) {
    GlobalScope.launch {
        val data = GlobalScope.async {
            return@async job()
        }.await()
        callback?.let {
            it(data)
        }
    }
}
