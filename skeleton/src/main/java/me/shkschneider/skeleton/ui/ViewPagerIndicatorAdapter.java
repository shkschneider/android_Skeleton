package me.shkschneider.skeleton.ui;


import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

/**
 * TODO override
 */
public class ViewPagerIndicatorAdapter extends FragmentStatePagerAdapter {

    public ViewPagerIndicatorAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return super.getPageTitle(position);
    }

    public Drawable getPageIcon(final int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(final int position) {
        return null;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object o) {
        return false;
    }

}
