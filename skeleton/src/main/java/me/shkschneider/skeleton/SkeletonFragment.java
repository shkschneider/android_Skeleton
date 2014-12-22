package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.skeleton.helpers.LogHelper;

public class SkeletonFragment extends Fragment {

    private int mLayout;

    @Deprecated
    public SkeletonFragment() {
        // Empty
    }

    @SuppressLint("ValidFragment")
    public SkeletonFragment(final int layout) {
        mLayout = layout;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        // Fragment cannot be used by itself (out of any container)
        if (container == null) {
            return null;
        }

        try {
            return inflater.inflate(mLayout, container, false);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
