package me.shkschneider.skeleton.android.util

import android.util.Base64

fun String.fromBase64(): String? =
    try {
        String(Base64.decode(this, Base64.DEFAULT), Charsets.UTF_8)
    } catch (e: IllegalArgumentException) {
        null
    }

fun String.toBase64(): String =
    Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
