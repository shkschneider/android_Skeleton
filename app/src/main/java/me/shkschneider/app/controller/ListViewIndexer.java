package me.shkschneider.app.controller;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helper.StringHelper;

public class ListViewIndexer<T> extends ArrayAdapter<T> implements SectionIndexer {

    private static final String SECTIONS = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Map<String, Integer> mIndexes = new LinkedHashMap<String, Integer>();
    private String mSections = "";

    public ListViewIndexer(final Context context, final int resource) {
        super(context, resource);
    }

    public ListViewIndexer(final Context context, final int resource, final int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ListViewIndexer(final Context context, final int resource, final T[] objects) {
        super(context, resource, objects);
    }

    public ListViewIndexer(final Context context, final int resource, final int textViewResourceId, final T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ListViewIndexer(final Context context, final int resource, final List<T> objects) {
        super(context, resource, objects);
    }

    public ListViewIndexer(final Context context, final int resource, final int textViewResourceId, final List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public void clear() {
        super.clear();
        mIndexes.clear();
        mSections = "";
    }

    @Override
    public void add(T object) {
        super.add(object);
        final String string = object.toString();
        if (StringHelper.nullOrEmpty(string)) {
            return ;
        }
        final String c = string.substring(0, 1).toUpperCase();
        final String s = (! SECTIONS.contains(c) ? String.valueOf(SECTIONS.charAt(0)) : c);
        if (! mSections.contains(s)) {
            mSections += s;
            mIndexes.put(s, getCount());
        }
    }

    @Override
    public void addAll(T... items) {
        for (final T item : items) {
            add(item);
        }
    }

    @Override
    public void addAll(Collection<? extends T> collection) {
        for (final T item : collection) {
            add(item);
        }
    }

    public int getPositionForSection(final int section) {
        final String[] sections = StringHelper.split(mSections);
        final String s = sections[section];
        if (! mIndexes.containsKey(s)) {
            return 0;
        }
        return mIndexes.get(s) - 1;
    }

    public int getSectionForPosition(final int position) {
        return 0;
    }

    public Object[] getSections() {
        return StringHelper.split(mSections);
    }

}
