package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @see me.shkschneider.skeleton.ui.MySwipeRefreshLayout
 */
public class MySwipeRefreshRecyclerView extends MySwipeRefreshLayout {

    private RecyclerView mRecyclerView;

    public MySwipeRefreshRecyclerView(final Context context) {
        this(context, null);
    }

    public MySwipeRefreshRecyclerView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView);
    }

    public RecyclerView recyclerView() {
        return mRecyclerView;
    }

}
