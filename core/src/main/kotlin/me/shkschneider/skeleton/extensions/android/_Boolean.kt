package me.shkschneider.skeleton.extensions.android

val Boolean.intValue: Int
    get() = if (this) 1 else 0
