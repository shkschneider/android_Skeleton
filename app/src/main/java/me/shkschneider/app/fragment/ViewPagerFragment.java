package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.StringHelper;

public class ViewPagerFragment extends SkeletonFragment {

    private static final int TABS = 2;

    public ViewPagerFragment() {
        title("ViewPager");
    }

    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
        refresh();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(mPagerAdapter);
        final PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagertabstrip);
        pagerTabStrip.setDrawFullUnderline(false);
        return view;
    }

    public void refresh() {
        mPagerAdapter.notifyDataSetChanged();
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(final int position) {
            return DummyFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return ((DummyFragment) getItem(position)).title();
        }

        @Override
        public int getCount() {
            return TABS;
        }

    }

    public static class DummyFragment extends SkeletonFragment {

        public static DummyFragment newInstance(final int position) {
            final DummyFragment dummyFragment = new DummyFragment();
            final Bundle bundle = new Bundle();
            bundle.putString(TITLE, String.format("DF #%d", position).toUpperCase());
            bundle.putInt(POSITION, position);
            dummyFragment.setArguments(bundle);
            return dummyFragment;
        }

        private static final String TITLE = "title";
        private static final String POSITION = "position";

        private ArrayAdapter<String> mAdapter;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            title(getArguments().getString(TITLE));
            final LayoutInflater layoutInflater = LayoutInflater.from(skeletonActivity());
            mAdapter = new ArrayAdapter<String>(skeletonActivity(), R.layout.listview_item1) {
                @Override
                public View getView(final int position, View convertView, final ViewGroup parent) {
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item1, parent, false);
                    }
                    ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
                    return convertView;
                }

                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                public boolean isEnabled(final int position) {
                    return false;
                }
            };
            refresh();
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_listview, container, false);
            final ListView listView = (ListView) view.findViewById(R.id.listview);
            listView.setAdapter(mAdapter);
            return view;
        }

        @Override
        public String title() {
            return getArguments().getString(TITLE);
        }

        public void refresh() {
            final int n = 2 * getArguments().getInt(POSITION);
            mAdapter.clear();
            for (int i = 0; i < n; i++) {
                mAdapter.add(StringHelper.random(n));
            }
            mAdapter.notifyDataSetChanged();
        }

    }

}
