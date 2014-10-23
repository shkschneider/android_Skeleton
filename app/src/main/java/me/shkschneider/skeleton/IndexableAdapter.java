package me.shkschneider.skeleton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;

/**
 * Intelligent ArrayAdapter that uses the toString() values of its items to automatically create sections in a ListView.
 * Everything is handled internally so getItem(int) considers the (previous) sections offset and so forth.
 * <br />
 * The getView() method return a section's view or null if it's a regular item.
 * It is then up to you to inflate the view of your items.
 * <br />
 * In such terms, yes, the recycling of the views is not really possible.
 *
 * - void withSections(ListView, String)
 * - void withSections(ListView)
 * - void withoutSections()
 *
 * @param <T> items
 */
public class IndexableAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

    private boolean mIndexed = true;
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Map<String, Integer> mIndexes = new LinkedHashMap<String, Integer>();

    // Constructors

    public IndexableAdapter(final Context context, final int resource) {
        super(context, resource);
    }

    public IndexableAdapter(final Context context, final int resource, final int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public IndexableAdapter(final Context context, final int resource, final T[] objects) {
        super(context, resource, objects);
    }

    public IndexableAdapter(final Context context, final int resource, final int textViewResourceId, final T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public IndexableAdapter(final Context context, final int resource, final List<T> objects) {
        super(context, resource, objects);
    }

    public IndexableAdapter(final Context context, final int resource, final int textViewResourceId, final List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    // Items

    @Override
    public void add(final T object) {
        super.add(object);
        if (mIndexed) {
            buildSections();
        }
    }

    @Override
    public void addAll(final Collection<? extends T> collection) {
        super.addAll(collection);
        if (mIndexed) {
            buildSections();
        }
    }

    @Override
    public void addAll(final T... items) {
        super.addAll(items);
        if (mIndexed) {
            buildSections();
        }
    }

    // Sections

    public void withSections(final ListView listView, final String sections) {
        listView.setFastScrollEnabled(true);
        mIndexed = true;
        mSections = sections;
    }

    public void withSections(final ListView listView) {
        withSections(listView, mSections);
    }

    public void withoutSections() {
        mIndexed = false;
    }

    private void buildSections() {
        if (! mIndexed) {
            return ;
        }

        mIndexes.clear();
        int offset = 0;
        for (int i = 0; i < super.getCount(); i++) {
            final Object object = super.getItem(i);
            final String string = object.toString();
            final String c = string.substring(0, 1).toUpperCase();
            final String s = (! mSections.contains(c) ? String.valueOf(mSections.charAt(0)) : c);
            if (! mIndexes.containsKey(s)) {
                mIndexes.put(s, i + offset);
                offset++;
            }
        }
        LogHelper.debug("sections:" + Arrays.asList(getSections()).toString());
    }

    // Adding the sections to the total count of items (for iteration and rendering)
    @Override
    public int getCount() {
        return (super.getCount() + mIndexes.size());
    }

    // Sections are one more type of view
    @Override
    public int getViewTypeCount() {
        return (super.getViewTypeCount() + 1);
    }

    // Not all items are enabled: sections are not
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(final int position) {
        return (! isSectionAtPosition(position));
    }

    private int offsetForPosition(final int position) {
        int offset = 0;
        for (final Map.Entry<String, Integer> entry : mIndexes.entrySet()) {
            if (entry.getValue() <= position) {
                offset++;
            }
            else {
                break ;
            }
        }
        return offset;
    }

    // Wee need to counteract the offset of sections in the item's count
    @Override
    public T getItem(final int position) {
        return super.getItem(position - offsetForPosition(position));
    }

    // This class only generates header views
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (! mIndexed) {
            return super.getView(position, convertView, parent);
        }

        if (isSectionAtPosition(position)) {
            final int section = getSectionForPosition(position);
            final LayoutInflater layoutInflater = LayoutInflater.from(SkeletonApplication.CONTEXT);
            convertView = layoutInflater.inflate(R.layout.listview_section, parent, false);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getSectionForSection(section));
            return convertView;
        }
        else {
            return null;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        buildSections();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        buildSections();
    }

    // FastScroll

    @Override
    public Object[] getSections() {
        String sections = "";
        for (final String section : mIndexes.keySet()) {
            sections += section;
        }
        return StringHelper.split(sections);
    }

    private boolean isSectionAtPosition(final int position) {
        return mIndexes.containsValue(position);
    }

    private String getSectionForSection(final int section) {
        int i = 0;
        for (final Map.Entry<String, Integer> entry : mIndexes.entrySet()) {
            if (i == section) {
                return entry.getKey();
            }
            i++;
        }
        return null;
    }

    @Override
    public int getPositionForSection(final int section) {
        // TODO improve so that the FastScroll pointer do not jump at sections
        int i = 0;
        for (final Map.Entry<String, Integer> entry : mIndexes.entrySet()) {
            if (i == section) {
                return entry.getValue();
            }
            i++;
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(final int position) {
        // TODO improve so that the FastScroll pointer do not jump at sections
        int i = 0;
        for (final Map.Entry<String, Integer> entry : mIndexes.entrySet()) {
            if (entry.getValue() == position) {
                return i;
            }
            i++;
        }
        return 0;
    }

}
