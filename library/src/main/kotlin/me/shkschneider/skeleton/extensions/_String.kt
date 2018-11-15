package me.shkschneider.skeleton.extensions

import androidx.annotation.IntRange

// Cases

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

// Ellipsize

fun String.ellipsize(@IntRange(from = 0) maxLength: Int, reverse: Boolean = false): String {
    if (length > maxLength) {
        if (reverse) {
            return Typography.ellipsis + substring((length - maxLength - 3), length)
        } else {
            return substring(0, maxLength - 3) + Typography.ellipsis
        }
    }
    return this
}
