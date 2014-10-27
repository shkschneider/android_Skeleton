package me.shkschneider.skeleton.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.app.MainApplication;
import me.shkschneider.app.R;

/**
 * Intelligent ReyclerView.Adapter<RecyclerView.ViewHolder> that uses its dataset to create sections.
 *
 * - void add(String)
 * - void addAll(List<String>)
 * - void clear()
 *
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @see android.support.v7.widget.RecyclerView.ViewHolder
 */
public class IndexedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;

        public SectionViewHolder(final View view) {
            super(view);
            this.textView1 = (TextView) view.findViewById(android.R.id.text1);
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;

        public ItemViewHolder(final View view) {
            super(view);
            this.textView1 = (TextView) view.findViewById(android.R.id.text1);
        }

    }

    public static final int TYPE_SECTION = 0;
    public static final int TYPE_ITEM = 1;

    private String SECTIONS = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private LayoutInflater mLayoutInflater;
    private Map<String, Integer> mIndexes = new LinkedHashMap<String, Integer>();
    private List<String> mDataset = new ArrayList<String>();

    public IndexedRecyclerAdapter() {
        mLayoutInflater = LayoutInflater.from(MainApplication.CONTEXT);
    }

    private void buildSections() {
        int offset = 0;
        for (int i = 0; i < mDataset.size(); i++) {
            final Object object = mDataset.get(i);
            final String string = object.toString();
            final String c = string.substring(0, 1).toUpperCase();
            final String s = (! SECTIONS.contains(c) ? String.valueOf(SECTIONS.charAt(0)) : c);
            if (! mIndexes.containsKey(s)) {
                mIndexes.put(s, i + offset);
                offset++;
            }
        }
    }

    public void add(final String dataset) {
        mDataset.add(dataset);
        buildSections();
    }

    public void addAll(final List<String> dataset) {
        mDataset.addAll(dataset);
        buildSections();
    }

    public void clear() {
        mDataset.clear();
        mIndexes.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == TYPE_SECTION) {
            final View view = mLayoutInflater.inflate(R.layout.listview_section, parent, false);
            return new SectionViewHolder(view);
        }
        else if (viewType == TYPE_ITEM) {
            final View view = mLayoutInflater.inflate(R.layout.listview_item1, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof SectionViewHolder) {
            ((SectionViewHolder) viewHolder).textView1.setText(sectionAt(position));
        }
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).textView1.setText(mDataset.get(position - offsetForPosition(position)));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (mIndexes.containsValue(position)) {
            return TYPE_SECTION;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (mDataset.size() + mIndexes.size());
    }

    private String sectionAt(final int position) {
        for (final Map.Entry<String, Integer> entry : mIndexes.entrySet()) {
            if (entry.getValue() == position) {
                return entry.getKey();
            }
        }
        return null;
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

}
