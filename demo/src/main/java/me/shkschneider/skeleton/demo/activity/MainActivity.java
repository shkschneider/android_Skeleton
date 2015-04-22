package me.shkschneider.skeleton.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.fragment.InputsFragment;
import me.shkschneider.skeleton.demo.fragment.ListViewFragment;
import me.shkschneider.skeleton.demo.fragment.SnackBarFragment;
import me.shkschneider.skeleton.demo.fragment.TransitionFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerCircleIndicatorFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerIconIndicatorFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerTextIndicatorFragment;
import me.shkschneider.skeleton.demo.fragment.FloatingActionButtonFragment;
import me.shkschneider.skeleton.demo.fragment.MainFragment;
import me.shkschneider.skeleton.demo.fragment.NetworkFragment;
import me.shkschneider.skeleton.SkeletonNavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.data.DiskCache;
import me.shkschneider.skeleton.data.MemoryCache;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ClassHelper;

public class  MainActivity extends SkeletonNavigationDrawerActivity {

    public static final int NAVIGATION_MAIN = 0;
    public static final int NAVIGATION_VIEWPAGERTEXTINDICATOR = 1;
    public static final int NAVIGATION_VIEWPAGERICONINDICATOR = 2;
    public static final int NAVIGATION_VIEWPAGERCIRCLEINDICATOR = 3;
    public static final int NAVIGATION_NETWORK = 4;
    public static final int NAVIGATION_LISTVIEW = 5;
    public static final int NAVIGATION_FLOATINGACTIONBUTTON = 6;
    public static final int NAVIGATION_SNACKBAR = 7;
    public static final int NAVIGATION_TRANSITION = 8;
    public static final int NAVIGATION_INPUTS = 9;

    // Anything as key, anything as value (LRU algorithm)
    private MemoryCache<String, Activity> mMemoryCache;
    // Anything as key, Bitmap as value (LRU algorithm)
    private MemoryCache.Bitmap mMemoryCacheBitmap;
    // String as key, Serializable as value (Serialization)
    private DiskCache.Internal mDiskCacheInternal;
    private DiskCache.External mDiskCacheExternal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMemoryCache = new MemoryCache<>();
        mMemoryCache.put("MainActivity", MainActivity.this);
        mMemoryCacheBitmap = new MemoryCache.Bitmap();
        mMemoryCacheBitmap.put("Bitmap", ApplicationHelper.icon());
        mDiskCacheInternal = new DiskCache.Internal();
        mDiskCacheInternal.put("DiskCache", "Internal");
        mDiskCacheExternal = new DiskCache.External();
        mDiskCacheExternal.put("DiskCache", "External");
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogHelper.info("MemoryCache:" + ClassHelper.canonicalName(mMemoryCache.get("MainActivity").getClass()));
        LogHelper.info("MemoryCacheBitmap:" + ClassHelper.canonicalName(mMemoryCacheBitmap.get("Bitmap").getClass()));
        LogHelper.info("DiskCacheInternal:" + mDiskCacheInternal.get("DiskCache").toString());
        LogHelper.info("DiskCacheExternal:" + mDiskCacheExternal.get("DiskCache").toString());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (navigationDrawer() != NAVIGATION_LISTVIEW) {
            searchable(null, null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(AboutActivity.getIntent(MainActivity.this));
            return true;
        }
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(SettingsActivity.getIntent(MainActivity.this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return new ArrayAdapter<SkeletonFragment>(this, R.layout.sk_listview_navigationdrawer_item, new ArrayList<SkeletonFragment>() {
            {
                add(NAVIGATION_MAIN, new MainFragment());
                add(NAVIGATION_VIEWPAGERTEXTINDICATOR, new ViewPagerTextIndicatorFragment());
                add(NAVIGATION_VIEWPAGERICONINDICATOR, new ViewPagerIconIndicatorFragment());
                add(NAVIGATION_VIEWPAGERCIRCLEINDICATOR, new ViewPagerCircleIndicatorFragment());
                add(NAVIGATION_NETWORK, new NetworkFragment());
                add(NAVIGATION_LISTVIEW, new ListViewFragment());
                add(NAVIGATION_FLOATINGACTIONBUTTON, new FloatingActionButtonFragment());
                add(NAVIGATION_SNACKBAR, new SnackBarFragment());
                add(NAVIGATION_TRANSITION, new TransitionFragment());
                add(NAVIGATION_INPUTS, new InputsFragment());
            }
        }) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                    convertView = layoutInflater.inflate(R.layout.sk_listview_navigationdrawer_item, parent, false);
                }
                final TextView textView = (TextView) convertView.findViewById(R.id.navigationDrawer_textView);
                textView.setText(getItem(position).title());
                if (position == navigationDrawer()) {
                    textView.setTextColor(getResources().getColor(R.color.accentColor));
                }
                else {
                    textView.setTextColor(getResources().getColor(R.color.textPrimaryColor));
                }
                return convertView;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }
        };
    }

    @Override
    protected SkeletonFragment getFragment(final int position) {
        return (SkeletonFragment) getAdapter().getItem(position);
    }

    @Override
    public void navigationDrawer(final int position) {
        super.navigationDrawer(position);
        subtitle(getFragment(position).title());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMemoryCache.clear();
        mMemoryCache = null;
        mMemoryCacheBitmap.clear();
        mMemoryCacheBitmap = null;
        mDiskCacheInternal.clear();
        mDiskCacheInternal = null;
        mDiskCacheExternal.clear();
        mDiskCacheExternal = null;
    }

}
