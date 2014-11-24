package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @see me.shkschneider.skeleton.ui.MySwipeRefreshLayout
 */
public class MySwipeRefreshListView extends MySwipeRefreshLayout {

    private ListView mListView;

    public MySwipeRefreshListView(final Context context) {
        this(context, null);
    }

    public MySwipeRefreshListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        mListView = new ListView(context);
        // getHeaderViewsCount()
        addView(mListView);
    }

    public ListView listView() {
        return mListView;
    }

}
