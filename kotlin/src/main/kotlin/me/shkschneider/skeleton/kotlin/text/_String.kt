package me.shkschneider.skeleton.kotlin.text

import java.util.Locale

// Cases

internal fun case(string: String): List<String> =
    string.toLowerCase(Locale.getDefault())
            .split("[^a-zA-Z]".toRegex())
            .filter { it.isNotBlank() }

fun String.camelCase(): String {
    return StringBuilder().apply {
        case(this@camelCase).forEach {
            append(if (isEmpty()) it else it.capitalize(Locale.getDefault()))
        }
    }.toString()
}

fun String.pascalCase(): String =
    camelCase().capitalize(Locale.getDefault())

fun String.snakeCase(): String =
    case(this).joinToString(separator = "_")

fun String.kebabCase(): String =
    case(this).joinToString(separator = "-")

// Trim

fun String.oneline() =
    replace(Regex("\\n"), " ")

// Ellipsize

fun String.ellipsize(maxLength: Int, reverse: Boolean = false): String {
    if (length > maxLength) {
        return if (reverse) {
            Typography.ellipsis + substring((length - maxLength - 3), length)
        } else {
            substring(0, maxLength - 3) + Typography.ellipsis
        }
    }
    return this
}
