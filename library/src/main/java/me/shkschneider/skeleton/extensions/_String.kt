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
        camelCase += (camelCase.isEmpty()) then it ?: it.capitalize()
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

fun String.ellipsize(@IntRange(from = 0) maxLength: Int = 80, reverse: Boolean = false): String {
    if (length > maxLength) {
        if (reverse) {
            return ".".repeat(3) + substring((length - maxLength - 3), length)
        } else {
            return substring(0, maxLength - 3) + ".".repeat(3)
        }
    }
    return this
}
