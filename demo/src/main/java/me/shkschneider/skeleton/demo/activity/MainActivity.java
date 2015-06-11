package me.shkschneider.skeleton.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.fragment.FloatingActionButtonFragment;
import me.shkschneider.skeleton.demo.fragment.InputsFragment;
import me.shkschneider.skeleton.demo.fragment.ListViewFragment;
import me.shkschneider.skeleton.demo.fragment.MainFragment;
import me.shkschneider.skeleton.SkeletonNavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.data.DiskCache;
import me.shkschneider.skeleton.data.MemoryCache;
import me.shkschneider.skeleton.demo.fragment.NetworkFragment;
import me.shkschneider.skeleton.demo.fragment.SnackBarFragment;
import me.shkschneider.skeleton.demo.fragment.TransitionFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerIconIndicatorFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerTextIndicatorFragment;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.Log;
import me.shkschneider.skeleton.helper.RunnableHelper;
import me.shkschneider.skeleton.java.ClassHelper;

public class  MainActivity extends SkeletonNavigationDrawerActivity {

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
        setNavigationHeader(R.layout.navigationdrawer_header);
        setNavigationMenu(R.menu.navigationdrawer);

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

        Log.i("MemoryCache:" + ClassHelper.canonicalName(mMemoryCache.get("MainActivity").getClass()));
        Log.i("MemoryCacheBitmap:" + ClassHelper.canonicalName(mMemoryCacheBitmap.get("Bitmap").getClass()));
        Log.i("DiskCacheInternal:" + mDiskCacheInternal.get("DiskCache").toString());
        Log.i("DiskCacheExternal:" + mDiskCacheExternal.get("DiskCache").toString());

        // FIXME overlap
        RunnableHelper.delay(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        navigationDrawer(R.id.menu_viewPagerTextIndicator);
                    }

                });
            }

        }, 800, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (navigationDrawer() != R.id.menu_listView) {
            searchable(null, null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(AboutActivity.getIntent(MainActivity.this));
                return true;
            case R.id.menu_settings:
                startActivity(SettingsActivity.getIntent(MainActivity.this));
                return true;
            case R.id.menu_main:
            case R.id.menu_viewPagerTextIndicator:
            case R.id.menu_viewPagerIconIndicator:
            case R.id.menu_network:
            case R.id.menu_listView:
            case R.id.menu_floatingActionButton:
            case R.id.menu_snackBar:
            case R.id.menu_transition:
            case R.id.menu_inputs:
                navigationDrawer(item.getItemId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private SparseArray<SkeletonFragment> mFragments = new SparseArray<SkeletonFragment>() {
        {
            put(R.id.menu_main, new MainFragment());
            put(R.id.menu_viewPagerTextIndicator, new ViewPagerTextIndicatorFragment());
            put(R.id.menu_viewPagerIconIndicator, new ViewPagerIconIndicatorFragment());
            put(R.id.menu_network, new NetworkFragment());
            put(R.id.menu_listView, new ListViewFragment());
            put(R.id.menu_floatingActionButton, new FloatingActionButtonFragment());
            put(R.id.menu_snackBar, new SnackBarFragment());
            put(R.id.menu_transition, new TransitionFragment());
            put(R.id.menu_inputs, new InputsFragment());
        }
    };

    @Override
    protected SkeletonFragment getFragment(final int itemId) {
        return mFragments.get(itemId, null);
    }

    @Override
    public void navigationDrawer(final int itemId) {
        super.navigationDrawer(itemId);
        subtitle(getFragment(itemId).title());
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
