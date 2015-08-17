package me.shkschneider.skeleton.java;

import java.util.Comparator;

// <http://www.davekoelle.com/files/AlphanumComparator.java>
public class AlphanumComparator implements Comparator<String> {

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private String getChunk(final String s, final int length, int marker) {
        final StringBuilder chunk = new StringBuilder();
        char c = s.charAt(marker);
        chunk.append(c);
        marker++;
        if (isDigit(c)) {
            while (marker < length) {
                c = s.charAt(marker);
                if (! isDigit(c)) {
                    break ;
                }
                chunk.append(c);
                marker++;
            }
        }
        else {
            while (marker < length) {
                c = s.charAt(marker);
                if (isDigit(c)) {
                    break ;
                }
                chunk.append(c);
                marker++;
            }
        }
        return chunk.toString();
    }

    @Override
    public int compare(final String s1, final String s2) {
        int m1 = 0;
        int m2 = 0;
        final int l1 = s1.length();
        final int l2 = s2.length();
        while (m1 < l1 && m2 < l2) {
            String thisChunk = getChunk(s1, l1, m1);
            m1 += thisChunk.length();
            String thatChunk = getChunk(s2, l2, m2);
            m2 += thatChunk.length();
            int result;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                final int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                if (result == 0) {
                    for (int i = 0; i < thisChunkLength; i++) {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            }
            else {
                result = thisChunk.compareTo(thatChunk);
            }
            if (result != 0) {
                return result;
            }
        }
        return (l1 - l2);
    }

}
