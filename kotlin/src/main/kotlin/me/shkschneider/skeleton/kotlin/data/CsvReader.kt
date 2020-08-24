package me.shkschneider.skeleton.kotlin.data

import me.shkschneider.skeleton.kotlin.log.Logger
import java.io.BufferedReader
import java.io.IOException

// <http://opencsv.sourceforge.net>
open class CsvReader(
        private val bufferedReader: BufferedReader,
        private val separator: Char = ',',
        private val quote: Char = '"',
        skip: Int = 0
) {

    private var hasNext = false

    @Throws(IOException::class)
    private fun getNextLine(): String {
        val nextLine = bufferedReader.readLine()
        nextLine ?: run {
            hasNext = false
            throw IOException("EOF")
        }
        return nextLine
    }

    fun hasNext(): Boolean {
        return hasNext
    }

    fun readNext(): List<String>? {
        try {
            val nextLine = getNextLine()
            return parseLine(nextLine)
        } catch (e: IOException) {
            return null
        }
    }

    @Throws(IOException::class)
    private fun parseLine(nextLine: String): List<String>? {
        var line: String = nextLine
        val tokensOnThisLine = ArrayList<String>()
        var stringBuffer = StringBuffer()
        var inQuotes = false
        do {
            if (inQuotes) {
                // continuing a quoted section, re-append newline
                stringBuffer.append("\n")
                line = getNextLine()
            }
            var i = 0
            while (i < line.length) {
                val c = line[i]
                if (c == quote) {
                    // this gets complex... the quote may end a quoted block, or escape another quote.
                    // do a 1-char lookahead:
                    if (inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
                            && line.length > i + 1  // there is indeed another character to check.
                            && line[i + 1] == quote) { // ..and that char. is a quote also.
                        // we have two quote chars in a row == one quote char, so consume them both and
                        // put one on the token. we do *not* exit the quoted text.
                        stringBuffer.append(line[i + 1])
                        i++
                    } else {
                        inQuotes = !inQuotes
                        // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                        if (i > 2 // not on the beginning of the line
                                && line[i - 1] != separator // not at the beginning of an escape sequence
                                && line.length > i + 1
                                && line[i + 1] != separator) { // not at the	end of an escape sequence
                            stringBuffer.append(c)
                        }
                    }
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(stringBuffer.toString())
                    stringBuffer = StringBuffer() // start work on next token
                } else {
                    stringBuffer.append(c)
                }
                i++
            }
        } while (inQuotes)
        tokensOnThisLine.add(stringBuffer.toString())
        return tokensOnThisLine
    }

    @Throws(IOException::class)
    fun close() {
        bufferedReader.close()
    }

    init {
        hasNext = true
        if (skip > 0) {
            try {
                for (i in 0 until skip) {
                    bufferedReader.readLine()
                }
            } catch (e: IOException) {
                Logger.warning(e.toString())
            }
        }
    }

}
