package me.shkschneider.skeleton.demo.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.activity.OverlayActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.ActivityTransitionHelper;
import me.shkschneider.skeleton.helper.SystemServices;

public class TransitionFragment extends SkeletonFragment {

    public TransitionFragment() {
        title("Transition");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transition, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new ArrayAdapter<Drawable>(skeletonActivity(), R.layout.gridview_item) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = SystemServices.layoutInflater().inflate(R.layout.gridview_item, parent, false);
                }
                final Drawable drawable = getResources().getDrawable(R.drawable.newyork);
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageDrawable(drawable);
                return convertView;
            }

            @Override
            public int getCount() {
                return 8;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Intent intent = OverlayActivity.getIntent(skeletonActivity());
                ActivityTransitionHelper.tag(view, "NewYork");
                ActivityTransitionHelper.transition(skeletonActivity(), intent, Pair.create(view, "NewYork"));
            }
        });
    }

}
