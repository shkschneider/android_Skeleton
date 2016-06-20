package me.shkschneider.skeleton.data;

import java.io.PrintWriter;
import java.io.Writer;

// <http://opencsv.sourceforge.net>
public class CsvWriter {

    private static final char NULL = '\0';

    private PrintWriter mPrintWriter;
    private char mSeparator;
    private char mQuote;
    private char mEscape;
    private String mEol;

    public CsvWriter(final Writer writer) {
        this(writer, ',', '"', '"', "\n");
    }

    public CsvWriter(final Writer writer, final char separator, final char quote, final char escape, final String eol) {
        mPrintWriter = new PrintWriter(writer);
        mSeparator = separator;
        mQuote = quote;
        mEscape = escape;
        mEol = eol;
    }

    public void writeNext(final String nextLine) {
        writeNext(nextLine.split(String.valueOf(mSeparator)));
    }

    public void writeNext(final String[] nextLine) {
        if (nextLine == null) {
            return ;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nextLine.length; i++) {
            if (i != 0) {
                stringBuilder.append(mSeparator);
            }
            final String nextElement = nextLine[i];
            if (nextElement == null) {
                continue;
            }
            if (mQuote != NULL) {
                stringBuilder.append(mQuote);
            }
            for (int j = 0; j < nextElement.length(); j++) {
                final char nextChar = nextElement.charAt(j);
                if (mEscape != NULL && nextChar == mQuote) {
                    stringBuilder.append(mEscape).append(nextChar);
                }
                else if (mEscape != NULL && nextChar == mEscape) {
                    stringBuilder.append(mEscape).append(nextChar);
                }
                else {
                    stringBuilder.append(nextChar);
                }
            }
            if (mQuote != NULL) {
                stringBuilder.append(mQuote);
            }
        }
        stringBuilder.append(mEol);
        mPrintWriter.write(stringBuilder.toString());
    }

}
