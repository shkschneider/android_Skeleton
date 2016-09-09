package me.shkschneider.skeleton.data;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.LogHelper;

// <http://opencsv.sourceforge.net>
public class CsvReader {

    private BufferedReader mBufferedReader;
    private boolean mHasNext;
    private char mSeparator;
    private char mQuote;

    public CsvReader(final Reader reader) {
        this(reader, ',', '"', 0);
    }

    public CsvReader(final Reader reader, final char separator, final char quote, final int skip) {
        mBufferedReader = new BufferedReader(reader);
        mSeparator = separator;
        mQuote = quote;
        mHasNext = true;
        if (skip > 0) {
            try {
                for (int i = 0; i < skip; i++) {
                    mBufferedReader.readLine();
                }
            }
            catch (final IOException e) {
                LogHelper.warning(e);
            }
        }
    }

    public boolean hasNext() {
        return mHasNext;
    }

    @Nullable
    public List<String> readNext() {
        try {
            final String nextLine = getNextLine();
            return parseLine(nextLine);
        }
        catch (final IOException e) {
            return null;
        }
    }

    private String getNextLine() throws IOException {
        final String nextLine = mBufferedReader.readLine();
        if (nextLine == null) {
            mHasNext = false;
            throw new IOException("EOF");
        }
        return nextLine;
    }

    @Nullable
    private List<String> parseLine(String nextLine) throws IOException {
        if (nextLine == null) {
            return null;
        }
        final List<String> tokensOnThisLine = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        boolean inQuotes = false;
        do {
            if (inQuotes) {
                // continuing a quoted section, re-append newline
                stringBuffer.append("\n");
                nextLine = getNextLine();
                if (nextLine == null) {
                    break;
                }
            }
            for (int i = 0; i < nextLine.length(); i++) {
                char c = nextLine.charAt(i);
                if (c == mQuote) {
                    // this gets complex... the quote may end a quoted block, or escape another quote.
                    // do a 1-char lookahead:
                    if (inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
                            && nextLine.length() > (i + 1)  // there is indeed another character to check.
                            && nextLine.charAt(i + 1) == mQuote) { // ..and that char. is a quote also.
                        // we have two quote chars in a row == one quote char, so consume them both and
                        // put one on the token. we do *not* exit the quoted text.
                        stringBuffer.append(nextLine.charAt(i + 1));
                        i++;
                    }
                    else {
                        inQuotes = ! inQuotes;
                        // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                        if (i > 2 // not on the beginning of the line
                                && nextLine.charAt(i - 1) != mSeparator // not at the beginning of an escape sequence
                                && nextLine.length() > (i + 1)
                                && nextLine.charAt(i + 1) != mSeparator) { // not at the	end of an escape sequence
                            stringBuffer.append(c);
                        }
                    }
                }
                else if (c == mSeparator && ! inQuotes) {
                    tokensOnThisLine.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer(); // start work on next token
                }
                else {
                    stringBuffer.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(stringBuffer.toString());
        return tokensOnThisLine;

    }

    public void close() throws IOException {
        mBufferedReader.close();
    }

}
