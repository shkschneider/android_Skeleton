package me.shkschneider.skeleton.data

import java.io.PrintWriter
import java.io.Writer

// <http://opencsv.sourceforge.net>
class CsvWriter {

    private val NULL = '\u0000'

    private val writer: PrintWriter
    private val separator: Char
    private val quote: Char
    private val escape: Char
    private val eol: String

    constructor(writer: Writer, separator: Char = ',', quote: Char = '"', escape: Char = '"', eol: String = "\n") {
        this.writer = PrintWriter(writer)
        this.separator = separator
        this.quote = quote
        this.escape = escape
        this.eol = eol
    }

    fun writeNext(nextLine: String) {
        writeNext(nextLine.split(separator.toString().toRegex()).toTypedArray())
    }

    fun writeNext(nextLine: Array<String>?) {
        nextLine ?: return
        val stringBuilder = StringBuilder()
        for (i in nextLine.indices) {
            if (i != 0) {
                stringBuilder.append(separator)
            }
            val nextElement = nextLine[i]
            if (quote != NULL) {
                stringBuilder.append(quote)
            }
            (0 until nextElement.length)
                    .map { nextElement[it] }
                    .forEach {
                        if (escape != NULL && it == quote) {
                            stringBuilder.append(escape).append(it)
                        } else if (escape != NULL && it == escape) {
                            stringBuilder.append(escape).append(it)
                        } else {
                            stringBuilder.append(it)
                        }
                    }
            if (quote != NULL) {
                stringBuilder.append(quote)
            }
        }
        stringBuilder.append(eol)
        writer.write(stringBuilder.toString())
    }

}
