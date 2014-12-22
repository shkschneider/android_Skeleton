package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentViewPager extends FragmentStatePagerAdapter {

    public FragmentViewPager(@NonNull final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(final int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public int size() {
        return 0;
    }

}