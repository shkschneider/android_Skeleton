package me.shkschneider.skeleton.extensions

import android.support.annotation.IntRange

internal fun case(string: String): List<String> {
    return string.toLowerCase()
            .split("[^a-zA-Z]".toRegex())
            .filter { it.isNotBlank() }
}

fun String.camelCase(): String {
    var camelCase = ""
    case(this).forEach {
        camelCase += if (camelCase.isEmpty()) it else it.capitalize()
    }
    return camelCase
}

fun String.pascalCase(): String {
    return camelCase().capitalize()
}

fun String.snakeCase(): String {
    return case(this).joinToString(separator = "_")
}

fun String.kebabCase(): String {
    return case(this).joinToString(separator = "-")
}

fun String.ellipsize(@IntRange(from = 0) maxLength: Int = 80): String {
    return if (length > maxLength) {
        substring(0, maxLength - 3) + ".".repeat(3)
    } else this
}