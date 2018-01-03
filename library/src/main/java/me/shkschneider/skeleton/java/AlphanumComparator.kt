package me.shkschneider.skeleton.java

import java.util.Comparator

// <http://www.davekoelle.com/files/AlphanumComparator.java>
class AlphanumComparator : Comparator<String> {

    private fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun getChunk(s: String, length: Int, m: Int): String {
        var marker = m
        val chunk = StringBuilder()
        var c = s[marker]
        chunk.append(c)
        marker++
        if (isDigit(c)) {
            while (marker < length) {
                c = s[marker]
                if (!isDigit(c)) {
                    break
                }
                chunk.append(c)
                marker++
            }
        } else {
            while (marker < length) {
                c = s[marker]
                if (isDigit(c)) {
                    break
                }
                chunk.append(c)
                marker++
            }
        }
        return chunk.toString()
    }

    override fun compare(s1: String, s2: String): Int {
        var m1 = 0
        var m2 = 0
        val l1 = s1.length
        val l2 = s2.length
        while (m1 < l1 && m2 < l2) {
            val thisChunk = getChunk(s1, l1, m1)
            m1 += thisChunk.length
            val thatChunk = getChunk(s2, l2, m2)
            m2 += thatChunk.length
            var result: Int
            if (isDigit(thisChunk[0]) && isDigit(thatChunk[0])) {
                val thisChunkLength = thisChunk.length
                result = thisChunkLength - thatChunk.length
                if (result == 0) {
                    for (i in 0 until thisChunkLength) {
                        result = thisChunk[i] - thatChunk[i]
                        if (result != 0) {
                            return result
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk)
            }
            if (result != 0) {
                return result
            }
        }
        return l1 - l2
    }

}
