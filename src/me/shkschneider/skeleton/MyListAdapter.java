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
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemHelper;

public class MyListAdapter extends ArrayAdapter<Map<String, String>> implements Filterable, SectionIndexer {

    private static final String SECTIONS = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LAYOUT = android.R.layout.simple_list_item_2;
    private static final String FILTER = "text1";

    private Context mContext;
    private List<Map<String, String>> mData;
    private List<Map<String, String>> mBackup;

    public MyListAdapter(final Context context, final int resource) {
        super(context, resource);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MyListAdapter(final Context context, final int resource, final Map<String, String>[] objects) {
        super(context, resource, objects);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId, final Map<String, String>[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MyListAdapter(final Context context, final int resource, final List<Map<String, String>> objects) {
        super(context, resource, objects);
    }

    public MyListAdapter(final Context context, final int resource, final int textViewResourceId, final List<Map<String, String>> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MyListAdapter(final Context context, final List<Map<String, String>> objects) {
        super(context, LAYOUT, objects);
        mContext = context;
        mData = objects;
        mBackup = new ArrayList<Map<String, String>>(mData);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) SystemHelper.systemService(mContext, SystemHelper.SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE))
                    .inflate(LAYOUT, null);
        }
        if (convertView != null) {
            final Map<String, String> object = mData.get(position);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(object.get("text1"));
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(object.get("text2"));
        }
        else {
            LogHelper.w("View was NULL");
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                mData.clear();
                final List<Map<String, String>> filteredData = new ArrayList<Map<String, String>>();
                for (final Map<String, String> data : mBackup) {
                    if (data.get(FILTER).toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        filteredData.add(data);
                    }
                }
                final FilterResults filterResults = new FilterResults();
                filterResults.count = filteredData.size();
                filterResults.values = filteredData;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(final CharSequence charSequence, final FilterResults filterResults) {
                mData.clear();
                mData.addAll((Collection<? extends Map<String, String>>) filterResults.values);
                notifyDataSetChanged();
            }

        };
    }

    @Override
    public Object[] getSections() {
        final List<String> sections = new ArrayList<String>();
        for (int i = 0; i < SECTIONS.length(); i++) {
            for (final Map<String, String> data : mData) {
                String c = String.valueOf(data.get(FILTER).charAt(0)).toUpperCase();
                if (! SECTIONS.contains(c)) {
                    c = String.valueOf(SECTIONS.charAt(0)).toUpperCase();
                }
                if (! sections.contains(c)) {
                    sections.add(c);
                }
            }
        }
        Collections.sort(sections);
        return sections.toArray();
    }

    @Override
    public int getPositionForSection(final int position) {
        final String c = String.valueOf(SECTIONS.charAt(position)).toUpperCase();
        for (int i = 0; i < mData.size(); i++) {
            if (String.valueOf(mData.get(i).get(FILTER).toUpperCase().charAt(0)).toUpperCase().equals(c)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(final int position) {
        final String c = String.valueOf(mData.get(position).get(FILTER).charAt(0)).toUpperCase();
        for (int i = 0; i < SECTIONS.length(); i++) {
            if (String.valueOf(SECTIONS.toUpperCase().charAt(i)).toUpperCase().equals(c)) {
                return i;
            }
        }
        return 0;
    }

}
