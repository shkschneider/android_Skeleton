package me.shkschneider.skeleton.extensions

import androidx.annotation.IntRange

// Cases

internal fun case(string: String): List<String> =
    string.toLowerCase()
            .split("[^a-zA-Z]".toRegex())
            .filter { it.isNotBlank() }

fun String.camelCase(): String {
    var camelCase = ""
    case(this).forEach {
        camelCase += if (camelCase.isEmpty()) it else it.capitalize()
    }
    return camelCase
}

fun String.pascalCase(): String =
    camelCase().capitalize()

fun String.snakeCase(): String =
    case(this).joinToString(separator = "_")

fun String.kebabCase(): String =
    case(this).joinToString(separator = "-")

// Trim

fun String.oneline() =
    replace(Regex("\\n"), " ")

// Ellipsize

fun String.limit(max: Int): Pair<String, String> =
    if (length > max) {
        take(max) to takeLast(length - max)
    } else this to ""

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
