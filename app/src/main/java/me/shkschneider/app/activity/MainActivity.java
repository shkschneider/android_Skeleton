package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.shkschneider.app.R;
import me.shkschneider.app.fragment.ListViewFragment;
import me.shkschneider.app.fragment.SnackBarFragment;
import me.shkschneider.app.fragment.TransitionFragment;
import me.shkschneider.app.fragment.ViewPagerCircleIndicatorFragment;
import me.shkschneider.app.fragment.ViewPagerIconIndicatorFragment;
import me.shkschneider.app.fragment.ViewPagerTextIndicatorFragment;
import me.shkschneider.app.fragment.FloatingActionButtonFragment;
import me.shkschneider.app.fragment.MainFragment;
import me.shkschneider.app.fragment.NetworkFragment;
import me.shkschneider.skeleton.SkeletonNavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.data.DiskCache;
import me.shkschneider.skeleton.data.MemoryCache;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SharedPreferencesHelper;
import me.shkschneider.skeleton.java.ClassHelper;

public class MainActivity extends SkeletonNavigationDrawerActivity {

    public static final int NAVIGATION_MAIN = 0;
    public static final int NAVIGATION_VIEWPAGERTEXTINDICATOR = 1;
    public static final int NAVIGATION_VIEWPAGERICONINDICATOR = 2;
    public static final int NAVIGATION_VIEWPAGERCIRCLEINDICATOR = 3;
    public static final int NAVIGATION_NETWORK = 4;
    public static final int NAVIGATION_LISTVIEW = 5;
    public static final int NAVIGATION_FLOATINGACTIONBUTTON = 6;
    public static final int NAVIGATION_SNACKBAR = 7;
    public static final int NAVIGATION_TRANSITION = 8;

    // Anything as key, anything as value (LRU algorithm)
    private MemoryCache<String, Activity> mMemoryCache;
    // Anything as key, Bitmap as value (LRU algorithm)
    private MemoryCache.Bitmap mMemoryCacheBitmap;
    // String as key, Serializable as value (Serialization)
    private DiskCache.Internal mDiskCacheInternal;
    private DiskCache.External mDiskCacheExternal;

    // SkeletonActivity.onHomeAsUpPressed()
    // IntentHelper.home()
    public static Intent getIntent(final Activity activity) {
        return IntentHelper.home();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMemoryCache = new MemoryCache<>();
        mMemoryCache.put("MainActivity", MainActivity.this);
        mMemoryCacheBitmap = new MemoryCache.Bitmap();
        mMemoryCacheBitmap.put("Bitmap", ApplicationHelper.icon());
        mDiskCacheInternal = new DiskCache.Internal();
        mDiskCacheInternal.put("DiskCacher", "Internal");
        mDiskCacheExternal = new DiskCache.External();
        mDiskCacheExternal.put("DiskCacher", "External");

        SharedPreferencesHelper.putPublic(TAB, String.valueOf(0));
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigationDrawer(Integer.valueOf(SharedPreferencesHelper.getPublic(TAB, String.valueOf(0))));
        LogHelper.info("MemoryCache:" + ClassHelper.canonicalName(mMemoryCache.get("MainActivity").getClass()));
        LogHelper.info("MemoryCacheBitmap:" + ClassHelper.canonicalName(mMemoryCacheBitmap.get("Bitmap").getClass()));
        LogHelper.info("DiskCacheInternal:" + mDiskCacheInternal.get("DiskCacher").toString());
        LogHelper.info("DiskCacheExternal:" + mDiskCacheExternal.get("DiskCacher").toString());
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
        return new ArrayAdapter<SkeletonFragment>(this, R.layout.listview_navigationdrawer_item, new ArrayList<SkeletonFragment>() {
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
            }
        }) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                    convertView = layoutInflater.inflate(R.layout.listview_navigationdrawer_item, parent, false);
                }
                final TextView textView = ((TextView) convertView.findViewById(R.id.navigationDrawer_textView));
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
    protected void onPause() {
        super.onPause();

        SharedPreferencesHelper.putPublic(TAB, String.valueOf(navigationDrawer()));
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
