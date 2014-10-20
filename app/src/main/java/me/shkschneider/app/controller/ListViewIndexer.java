package me.shkschneider.app.controller;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

public class ListViewIndexer extends ArrayAdapter<String> implements SectionIndexer {

    private String SECTIONS = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ListViewIndexer(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getPositionForSection(final int section) {
        for (int i = 0; i < getCount(); i++) {
            final String item = getItem(i).toUpperCase();
            if (item.charAt(0) == SECTIONS.charAt(section)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(final int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        // TODO only used sections
        final String[] sections = new String[SECTIONS.length()];
        for (int i = 0; i < SECTIONS.length(); i++) {
            sections[i] = String.valueOf(SECTIONS.charAt(i));
        }
        return sections;
    }

}
