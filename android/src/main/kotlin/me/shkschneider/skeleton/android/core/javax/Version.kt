package me.shkschneider.skeleton.android.core.javax

open class Version(version: String) : Comparable<Version> {

    private var version: List<String> = version.split(".")

    // <https://semver.org>
    fun semVer(): Boolean {
        val parts = version.toString().takeWhile { it.isDigit() || it == '.' }.split(".")
        if (parts.size == 3) {
            parts.forEach {
                it.toIntOrNull() ?: return false
            }
            return true
        }
        return false
    }

    override fun compareTo(other: Version): Int {
        val version2 = other.toList()
        val size = version.size.coerceAtLeast(version2.size)
        0.rangeTo(size).forEach { i ->
            try {
                // Process as number
                val i1: Int = if (i < version.size) Integer.valueOf(version[i]) else 0
                val i2: Int = if (i < version2.size) Integer.valueOf(version2[i]) else 0
                if (i1 < i2) return -1
                if (i1 > i2) return 1
            } catch (e: NumberFormatException) {
                // Process as string
                return version[i].compareTo(version2[i])
            }
        }
        return 0
    }

    private fun toList(): List<String> {
        return version
    }

    override fun toString(): String {
        return version.joinToString(".")
    }

    class Comparator : java.util.Comparator<Version> {

        override fun compare(v1: Version?, v2: Version?): Int {
            v1 ?: return -1
            v2 ?: return 1
            return v1.compareTo(v2)
        }

    }

}
