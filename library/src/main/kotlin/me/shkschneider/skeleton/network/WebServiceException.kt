package me.shkschneider.skeleton.network

import android.support.annotation.IntRange
import java.util.*

class WebServiceException : Exception {

    val code: Int

    constructor(@IntRange(from = 0, to = INTERNAL_ERROR.toLong()) code: Int, message: String? = null) : super(message, null) {
        this.code = code
    }

    override fun toString(): String {
        return String.format(Locale.getDefault(), "%d %s", code, message)
    }

    companion object {

        const val INTERNAL_ERROR = 666

    }

}
