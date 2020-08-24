package me.shkschneider.skeleton.kotlin.datax

import java.util.Base64

// FIXME unsafe API calls
fun ByteArray.toBase64(): String =
    Base64.getEncoder().encodeToString(this)

// FIXME unsafe API calls
fun String.fromBase64(): ByteArray =
    Base64.getDecoder().decode(this)
