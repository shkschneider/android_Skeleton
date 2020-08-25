package me.shkschneider.skeleton.android.util

import android.util.Base64
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull

fun String.fromBase64(): String? =
    tryOrNull {
        String(Base64.decode(this, Base64.DEFAULT), Charsets.UTF_8)
    }

fun String.toBase64(): String =
    Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
