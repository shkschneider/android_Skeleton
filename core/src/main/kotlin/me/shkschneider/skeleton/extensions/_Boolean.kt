package me.shkschneider.skeleton.extensions

val Boolean.TRUE: Boolean
    get() = true

val Boolean.FALSE: Boolean
    get() = false

val Boolean.stringValue: String
    get() = if (this == true) "true" else "false"

val Boolean.intValue: Int
    get() = if (this == true) 1 else 0
