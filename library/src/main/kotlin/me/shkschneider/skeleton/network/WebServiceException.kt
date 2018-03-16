package me.shkschneider.skeleton.network

import android.support.annotation.IntRange
import java.util.*

class WebServiceException : Exception {

    private val _code: Int
    private val _message: String

    constructor(@IntRange(from = 0, to = INTERNAL_ERROR.toLong()) code: Int, message: String) {
        _code = code
        _message = message
    }

    fun code(): Int {
        return _code
    }

    fun message(): String {
        return _message
    }

    override fun toString(): String {
        return String.format(Locale.getDefault(), "%d %s", code(), message())
    }

    companion object {

        const val INTERNAL_ERROR = 666

    }

}
