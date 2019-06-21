package me.shkschneider.skeleton.kotlinx

import java.util.Base64

fun String.fromBase64(): String? = try {
    String(Base64.getDecoder().decode(toByteArray()))
} catch (e: IllegalArgumentException) {
    null
}

fun String.toBase64(): String = Base64.getEncoder().encodeToString(toByteArray())
