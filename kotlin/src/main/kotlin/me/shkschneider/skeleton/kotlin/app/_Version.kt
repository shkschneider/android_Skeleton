package me.shkschneider.skeleton.kotlin.app

fun Version.toSemVer() = SemanticVersion(toString())
