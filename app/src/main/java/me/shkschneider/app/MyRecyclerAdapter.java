package me.shkschneider.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<MyItem> mMyItems = new ArrayList<MyItem>();
    private int mLayout = 0;
    private Callback mCallback = null;

    public MyRecyclerAdapter(final int layout) {
        mLayout = layout;
    }

    public List<MyItem> myItems() {
        return mMyItems;
    }

    public void setCallback(final Callback callback) {
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(mLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final MyItem myItem = mMyItems.get(position);
        viewHolder.text1.setText(myItem.text1);
        viewHolder.text2.setText(myItem.text2);
    }

    @Override
    public int getItemCount() {
        return mMyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text1;
        public TextView text2;

        public ViewHolder(final View view) {
            super(view);
            text1 = (TextView) view.findViewById(android.R.id.text1);
            text2 = (TextView) view.findViewById(android.R.id.text2);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    mCallback.onItemClick(getPosition());
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    return mCallback.onItemLongClick(getPosition());
                }
            });
        }

    }

    public static class MyItem {

        public String text1;
        public String text2;

        public MyItem(final String text1, final String text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

    }

    public interface Callback {

        public void onItemClick(final int position);
        public boolean onItemLongClick(final int position);

    }

}
