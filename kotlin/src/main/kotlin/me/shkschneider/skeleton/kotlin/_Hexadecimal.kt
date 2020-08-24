package me.shkschneider.skeleton.kotlin

import java.util.Locale
import javax.xml.bind.DatatypeConverter

fun ByteArray.toHexadecimal(): String =
    DatatypeConverter.printHexBinary(this)

val String.isHexadecimal: Boolean
    get() = toLowerCase(Locale.getDefault()).all {
        it in ('a' until 'f') || it.isDigit()
    }
