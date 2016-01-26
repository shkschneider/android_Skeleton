package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

// <https://guides.codepath.com/android/using-the-recyclerview>
public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(final Context context) {
        this(context, null);
    }

    public MyRecyclerView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setHasFixedSize(true);
        addItemDecoration(new MyRecyclerViewSeparator(context, MyRecyclerViewSeparator.VERTICAL));
    }

}
