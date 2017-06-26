package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// <https://gist.github.com/unosk/af99b1a97b2f48521cee>
public abstract class MyRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Object mLock = new Object();
    private final List<T> mObjects = new ArrayList<>();

    public MyRecyclerViewAdapter(@Nullable final List<T> objects) {
        if (objects != null) {
            mObjects.addAll(objects);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public T getItem(final int position) {
        return mObjects.get(position);
    }

    public List<T> getItems() {
        return mObjects;
    }

    public void add(final T object, final boolean notify) {
        synchronized (mLock) {
            mObjects.add(object);
        }
        if (notify) notifyDataSetChanged();
    }

    public void insert(final T object, final int index, final boolean notify) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }
        if (notify) notifyDataSetChanged();
    }

    public void remove(final T object, final boolean notify) {
        synchronized (mLock) {
            mObjects.remove(object);
        }
        if (notify) notifyDataSetChanged();
    }

    public void addAll(final List<T> objects, final boolean notify) {
        synchronized (mLock) {
            mObjects.addAll(objects);
        }
        if (notify) notifyDataSetChanged();
    }

    public void clear(final boolean notify) {
        synchronized (mLock) {
            mObjects.clear();
        }
        if (notify) notifyDataSetChanged();
    }

    public void sort(final Comparator<? super T> comparator, final boolean notify) {
        synchronized (mLock) {
            Collections.sort(mObjects, comparator);
        }
        if (notify) notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return (mObjects == null || mObjects.isEmpty());
    }

    @SuppressWarnings("unused")
    private static class MyViewHolder<T> extends RecyclerView.Adapter<MyViewHolder.ViewHolder> {

        private List<T> mObjects;

        public MyViewHolder(@NonNull final List<T> objects) {
            mObjects = objects;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            // Inflated items should have: android:foreground="?android:attr/selectableItemBackground"
            // <http://stackoverflow.com/q/26961147>
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder.ViewHolder holder, final int position) {
            final T object = mObjects.get(position);
            holder.text1.setText(object.toString());
            holder.itemView.setOnClickListener(null);
            holder.itemView.setOnLongClickListener(null);
        }

        @Override
        public int getItemCount() {
            return mObjects.size();
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text1;

            public ViewHolder(final View itemView) {
                super(itemView);

                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }

        }

    }

}
