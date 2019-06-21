package me.shkschneider.skeleton.extensions

import androidx.annotation.IntRange
import java.lang.StringBuilder

const val ELLIPSIS = "…"

operator fun String?.plus(s: String?): String? =
        if (this == null) s else if (s == null) this else this + s

// "camelCase"
fun String.camelCase() =
        StringBuilder().also { stringBuilder ->
            normalizedLowerCaseList(this).forEach {
                stringBuilder.append(it.capitalize())
            }
        }.toString()

// "PasalCase"
fun String.pascalCase(): String =
        camelCase().capitalize()

// "snake_case"
fun String.snakeCase(): String =
        normalizedLowerCaseList(this).joinToString(separator = "_")

// "kebab-case"
fun String.kebabCase(): String =
        normalizedLowerCaseList(this).joinToString(separator = "-")

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

// Cases

internal fun normalizedLowerCaseList(string: String): List<String> =
        string.toLowerCase()
                .split("[^a-zA-Z]".toRegex())
                .filter { it.isNotBlank() }
