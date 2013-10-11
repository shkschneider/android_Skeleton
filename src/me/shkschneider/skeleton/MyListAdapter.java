/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helper.ListHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;
import me.shkschneider.skeleton.helper.SystemHelper;

public class MyListAdapter extends ArrayAdapter<Map<String, Object>> implements Filterable, SectionIndexer {

    protected static final String SECTIONS = "#" + StringHelper.ALPHA.toUpperCase();

    private Context mContext;
    private Integer mLayout;
    private List<String> mSections;
    private List<Data> mData;
    private List<Data> mBackup;
    private Callback mCallback;

    public MyListAdapter(final Context context, final int resource) {
        super(context, resource);
        init(context, resource, null, null);
    }

    public MyListAdapter(final Context context, final int resource, final Data[] objects) {
        super(context, resource);
        init(context, resource, objects, null);
    }

    public MyListAdapter(final Context context, final int resource, final Data[] objects, final Callback callback) {
        super(context, resource);
        init(context, resource, objects, callback);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId) {
        super(context, resource, textViewResourceId);
        init(context, resource, null, null);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId, final Data[] objects) {
        super(context, resource, textViewResourceId);
        init(context, resource, objects, null);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId, final Data[] objects, final Callback callback) {
        super(context, resource, textViewResourceId);
        init(context, resource, objects, callback);
    }

    protected void init(final Context context, final int resource, final Data[] objects, final Callback callback) {
        mContext = context;
        mLayout = resource;
        mSections = null;
        mData = Arrays.asList(objects);
        mBackup = new ArrayList<Data>(mData);
        mCallback = callback;
    }

    public List<Data> get() {
        return mData;
    }

    public Data get(final int position) {
        if (mData == null || position < 0 || position >= mData.size()) {
            return null;
        }

        return mData.get(position);
    }

    public void index(final ListView listView) {
        if (listView == null) {
            LogHelper.w("ListView was NULL");
            return ;
        }

        if (mData != null) {
            Collections.sort(mData, new Comparator<Data>() {

                @Override
                public int compare(final Data d1, final Data d2) {
                    return d1.key.compareToIgnoreCase(d2.key);
                }

            });
            notifyDataSetChanged();
        }

        listView.setFastScrollEnabled(true);
        listView.smoothScrollToPosition(0);
    }

    public void clear() {
        mData = null;
    }

    public static class Data {

        protected String key;
        protected Object value;

        public Data(final String key, final Object value) {
            this.key = key;
            this.value = value;
        }

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null && mContext != null && mLayout > 0) {
            final LayoutInflater layoutInflater = (LayoutInflater) SystemHelper.systemService(mContext, SystemHelper.SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(mLayout, null);
        }

        if (convertView == null) {
            LogHelper.w("ConvertView was NULL");
            return null;
        }

        final Data data = get(position);

        if (data == null) {
            LogHelper.w("Data was NULL");
            return convertView;
        }

        if (mCallback == null) {
            LogHelper.w("Callback was NULL");
            return convertView;
        }

        return mCallback.myListAdapterCallback(convertView, data);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                clear();
                final List<Object> objects = new ArrayList<Object>();
                for (final Data data : mBackup) {
                    if (data.key.toUpperCase().startsWith(constraint.toString().toLowerCase())) {
                        objects.add(data);
                    }
                }
                final FilterResults filterResults = new FilterResults();
                filterResults.count = objects.size();
                filterResults.values = ListHelper.array(objects);
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(final CharSequence charSequence, final FilterResults filterResults) {
                clear();
                mData = (List<Data>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    @Override
    public Object[] getSections() {
        mSections = new ArrayList<String>();
        for (int i = 0; i < SECTIONS.length(); i++) {
            for (final Data data : mData) {
                String c = String.valueOf(data.key.charAt(0)).toUpperCase();
                if (! mSections.contains(c)) {
                    mSections.add(c);
                }
            }
        }
        return mSections.toArray();
    }

    @Override
    public int getPositionForSection(int position) {
        position = (position <= 0 ? 0 : position);
        position = (position >= mSections.size() ? mSections.size() - 1 : position);
        final String c = String.valueOf(mSections.get(position).charAt(0)).toUpperCase();
        for (int i = 0; i < mData.size(); i++) {
            if (String.valueOf(get(i).key.toUpperCase().charAt(0)).toUpperCase().equals(c)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        position = (position <= 0 ? 0 : position);
        position = (position >= mSections.size() ? mSections.size() - 1 : position);
        final Data data = get(position);
        if (data != null) {
            final String c = String.valueOf(data.key.charAt(0)).toUpperCase();
            for (int i = 0; i < SECTIONS.length(); i++) {
                if (String.valueOf(SECTIONS.toUpperCase().charAt(i)).toUpperCase().equals(c)) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    public int getCount() {
        return (mData == null ? super.getCount() : mData.size());
    }

    public static interface Callback {

        public View myListAdapterCallback(final View view, final Data data);

    }

}
