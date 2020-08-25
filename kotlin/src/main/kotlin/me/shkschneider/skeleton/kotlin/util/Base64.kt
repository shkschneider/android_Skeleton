package me.shkschneider.skeleton.kotlin.util

import java.util.Base64

// FIXME API_26?
fun ByteArray.toBase64(): String =
    Base64.getEncoder().encodeToString(this)

// FIXME API_26
fun String.fromBase64(): ByteArray =
    Base64.getDecoder().decode(this)
