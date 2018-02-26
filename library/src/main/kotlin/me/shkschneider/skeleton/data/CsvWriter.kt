package me.shkschneider.skeleton.data

import java.io.PrintWriter
import java.io.Writer

// <http://opencsv.sourceforge.net>
class CsvWriter {

    private val NULL = '\u0000'
    private val _writer: PrintWriter
    private val _separator: Char
    private val _quote: Char
    private val _escape: Char
    private val _eol: String

    constructor(writer: Writer, separator: Char = ',', quote: Char = '"', escape: Char = '"', eol: String = "\n") {
        _writer = PrintWriter(writer)
        _separator = separator
        _quote = quote
        _escape = escape
        _eol = eol
    }

    fun writeNext(nextLine: String) {
        writeNext(nextLine.split(_separator.toString().toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
    }

    fun writeNext(nextLine: Array<String>?) {
        if (nextLine == null) {
            return
        }
        val stringBuilder = StringBuilder()
        for (i in nextLine.indices) {
            if (i != 0) {
                stringBuilder.append(_separator)
            }
            val nextElement = nextLine[i]
            if (_quote != NULL) {
                stringBuilder.append(_quote)
            }
            (0 until nextElement.length)
                    .map { nextElement[it] }
                    .forEach {
                        if (_escape != NULL && it == _quote) {
                            stringBuilder.append(_escape).append(it)
                        } else if (_escape != NULL && it == _escape) {
                            stringBuilder.append(_escape).append(it)
                        } else {
                            stringBuilder.append(it)
                        }
                    }
            if (_quote != NULL) {
                stringBuilder.append(_quote)
            }
        }
        stringBuilder.append(_eol)
        _writer.write(stringBuilder.toString())
    }

}
