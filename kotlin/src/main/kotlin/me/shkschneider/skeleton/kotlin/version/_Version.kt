package me.shkschneider.skeleton.kotlin.version

fun Version.toSemVer() = SemanticVersion(toString())
