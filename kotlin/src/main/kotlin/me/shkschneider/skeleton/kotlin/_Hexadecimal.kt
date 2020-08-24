package me.shkschneider.skeleton.kotlin

import javax.xml.bind.DatatypeConverter

fun ByteArray.toHexadecimal(): String =
    DatatypeConverter.printHexBinary(this)

val String.isHexadecimal: Boolean
    get() = toLowerCase().all {
        it in ('a' until 'f') || it.isDigit()
    }
