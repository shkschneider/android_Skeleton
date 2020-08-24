package me.shkschneider.skeleton.android.core.jvm

private const val MAJOR = 0
private const val MINOR = 1
private const val PATCH = 2

// <https://semver.org>
open class SemanticVersion : Comparable<SemanticVersion> {

    private var semVer = ArrayList<Int>()
    private val semanticVersioning = listOf(MAJOR, MINOR, PATCH)

    @Throws(IllegalArgumentException::class)
    constructor(version: String) {
        val parts = version.takeWhile { it.isDigit() || it == '.' }.split(".")
        if (parts.size != semanticVersioning.size) {
            throw IllegalArgumentException("Not MAJOR.MINOR.PATCH.")
        }
        parts.forEach { part ->
            part.toIntOrNull()?.let {
                semVer.add(it)
            } ?: throw IllegalArgumentException("Not an Int.")
            // SemVer v2 allows appendix after last element
        }
    }

    @Throws(IllegalArgumentException::class)
    constructor(version: Version) : this(version.toString())

    override fun compareTo(other: SemanticVersion): Int {
        semanticVersioning.forEach { i ->
            if (semVer[i] < other.toList()[i]) return -1
            if (semVer[i] > other.toList()[i]) return 1
        }
        return 0
    }

    private fun toList(): List<Int> {
        return semVer
    }

    fun major(): Int {
        return semVer[MAJOR]
    }

    fun minor(): Int {
        return semVer[MINOR]
    }

    fun patch(): Int {
        return semVer[PATCH]
    }

    override fun toString(): String {
        return semVer.joinToString(".")
    }

    class Comparator : java.util.Comparator<SemanticVersion> {

        @Throws(IllegalArgumentException::class)
        override fun compare(v1: SemanticVersion?, v2: SemanticVersion?): Int {
            v1 ?: return -1
            v2 ?: return 1
            return v1.compareTo(v2)
        }

    }

}
