package me.shkschneider.skeleton;

import android.os.Bundle;

import me.shkschneider.skeleton.ui.FragmentHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SkeletonFragmentActivity extends SkeletonActivity {

    private static final int LAYOUT = R.layout.fragment_activity;
    private static final int ID = R.id.fragment;

    private List<SkeletonFragment> mFragments;
    private int mPosition;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        mFragments = new ArrayList<SkeletonFragment>();
        mPosition = 0;
    }

    @Override
    public void onBackPressed() {
        if (! back()) {
            super.onBackPressed();
        }
    }

    public boolean fragment(@NotNull final SkeletonFragment fragment) {
        mFragments.add(fragment);
        if (FragmentHelper.replace(SkeletonFragmentActivity.this, ID, fragment)) {
            mPosition++;
            return true;
        }

        return false;
    }

    public int position() {
        return mPosition;
    }

    public boolean back() {
        if (mPosition <= 0) {
            return false;
        }

        mPosition--;
        if (fragment(mFragments.get(position()))) {
            mPosition--;
            return true;
        }

        return false;
    }

}
