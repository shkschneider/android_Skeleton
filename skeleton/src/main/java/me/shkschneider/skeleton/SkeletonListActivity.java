package me.shkschneider.skeleton;

import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SkeletonListActivity extends SkeletonActivity {

    private ListView mListView;

    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(android.R.id.list);
        }
        return mListView;
    }

    protected void setListAdapter(final ListAdapter listAdapter) {
        getListView().setAdapter(listAdapter);
    }

    protected ListAdapter getListAdapter() {
        final ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        else {
            return adapter;
        }
    }

    protected void onListItemClick(final ListView listView, final View view, final int position, final long id) {
        getListView().getOnItemClickListener().onItemClick(listView, view, position, id);
    }

}
