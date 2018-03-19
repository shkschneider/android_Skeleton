package me.shkschneider.skeleton.extensions

import android.support.annotation.IntRange

fun String.camelCase(): String {
    with(split("[^a-zA-Z]".toRegex()).toList()) {
        filter { !it.isBlank() }.forEach {
            var camelCase = this[0].toLowerCase()
            subList(1, size).forEach {
                camelCase += it.capitalize()
            }
            return camelCase
        }
    }
    return ""
}

fun String.ellipsize(@IntRange(from = 0) maxLength: Int = 80): String {
    return if (length > maxLength) {
        substring(0, maxLength - 3) + "..."
    } else this
}