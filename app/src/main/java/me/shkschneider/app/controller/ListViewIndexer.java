package me.shkschneider.app.controller;

import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Comparator;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonApplication;

public class ListViewIndexer extends BaseAdapter {

    private static final String SECTIONS_AVAILABLE = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String SECTIONS_USED = "";

    private boolean mValid = true;
    private ListAdapter mAdapter;
    private SparseArray<Section> mSections = new SparseArray<Section>();

    public ListViewIndexer(final ListAdapter listAdapter) {
        mAdapter = listAdapter;
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mValid = ! mAdapter.isEmpty();
                notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                mValid = false;
                notifyDataSetInvalidated();
            }
        });

        // auto-sections
        int offset = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final Object object = mAdapter.getItem(i);
            final String c = object.toString().substring(0, 1).toUpperCase();
            final String s = (! SECTIONS_AVAILABLE.contains(c) ? String.valueOf(SECTIONS_AVAILABLE.charAt(0)) : c);
            if (! SECTIONS_USED.contains(s)) {
                final Section section = new Section(i, s);
                section.sectionedPosition = section.firstPosition + offset;
                mSections.append(section.sectionedPosition, section);
                SECTIONS_USED += s;
                offset++;
            }
        }
        notifyDataSetChanged();
    }

    public void setSections(final Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o1, Section o2) {
                return ((o1.firstPosition == o2.firstPosition)
                        ? 0
                        : ((o1.firstPosition < o2.firstPosition) ? -1 : 1));
            }
        });

        int offset = 0;
        for (final Section section : sections) {
            section.sectionedPosition = section.firstPosition + offset;
            mSections.append(section.sectionedPosition, section);
            offset++;
        }

        notifyDataSetChanged();
    }

    public int positionToSectionedPosition(final int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).firstPosition > position) {
                break ;
            }
            offset++;
        }
        return (position + offset);
    }

    public int sectionedPositionToPosition(final int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return ListView.INVALID_POSITION;
        }
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
                break ;
            }
            offset--;
        }
        return (sectionedPosition + offset);
    }

    public boolean isSectionHeaderPosition(final int position) {
        return (mSections.get(position) != null);
    }

    @Override
    public int getCount() {
        return (mValid ? (mAdapter.getCount() + mSections.size()) : 0);
    }

    @Override
    public Object getItem(final int position) {
        return (isSectionHeaderPosition(position)
                ? mSections.get(position)
                : mAdapter.getItem(sectionedPositionToPosition(position)));
    }

    @Override
    public long getItemId(final int position) {
        return (isSectionHeaderPosition(position)
                ? (Integer.MAX_VALUE - mSections.indexOfKey(position))
                : mAdapter.getItemId(sectionedPositionToPosition(position)));
    }

    @Override
    public int getItemViewType(final int position) {
        return (isSectionHeaderPosition(position)
                ? (getViewTypeCount() - 1)
                : mAdapter.getItemViewType(position));
    }

    @Override
    public boolean isEnabled(final int position) {
        //noinspection SimplifiableConditionalExpression
        return (isSectionHeaderPosition(position)
                ? false
                : mAdapter.isEnabled(sectionedPositionToPosition(position)));
    }

    @Override
    public int getViewTypeCount() {
        return (mAdapter.getViewTypeCount() + 1);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (isSectionHeaderPosition(position)) {
            final LayoutInflater layoutInflater = LayoutInflater.from(SkeletonApplication.CONTEXT);
            convertView = layoutInflater.inflate(R.layout.listview_section, parent, false);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(mSections.get(position).title);
            return convertView;
        }
        else {
            return mAdapter.getView(sectionedPositionToPosition(position), convertView, parent);
        }
    }

    public static class Section {

        public int firstPosition;
        public int sectionedPosition;
        public CharSequence title;

        public Section(final int firstPosition, final CharSequence title) {
            this.firstPosition = firstPosition;
            this.title = title;
        }

        public CharSequence getTitle() {
            return title;
        }

    }

}
