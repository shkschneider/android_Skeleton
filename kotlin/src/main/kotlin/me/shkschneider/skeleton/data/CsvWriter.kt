package me.shkschneider.skeleton.data

import java.io.PrintWriter
import java.io.Writer

private const val NULL = '\u0000'

// <http://opencsv.sourceforge.net>
open class CsvWriter(
        writer: Writer,
        private val separator: Char = ',',
        private val quote: Char = '"',
        private val escape: Char = '"',
        private val eol: String = "\n"
) {

    private val writer: PrintWriter = if (writer is PrintWriter) writer else PrintWriter(writer)

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
