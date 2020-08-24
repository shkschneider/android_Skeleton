package me.shkschneider.skeleton.version

fun Version.toSemVer() = SemanticVersion(toString())
