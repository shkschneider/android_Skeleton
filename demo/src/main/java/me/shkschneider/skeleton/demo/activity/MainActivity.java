package me.shkschneider.skeleton.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.fragment.FloatingActionButtonFragment;
import me.shkschneider.skeleton.demo.fragment.InputsFragment;
import me.shkschneider.skeleton.demo.fragment.RecyclerViewFragment;
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
import me.shkschneider.skeleton.helper.LogHelper;
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

        // PermissionsHelper.revocable(PermissionsHelper.INTERNET);
        // if (PermissionsHelper.revocable(PermissionsHelper.READ_PHONE_STATE)) {
        //     PermissionsHelper.request(MainActivity.this, new String[] { PermissionsHelper.READ_PHONE_STATE });
        // }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogHelper.info("MemoryCache: " + ClassHelper.canonicalName(mMemoryCache.get("MainActivity").getClass()));
        LogHelper.info("MemoryCacheBitmap: " + ClassHelper.canonicalName(mMemoryCacheBitmap.get("Bitmap").getClass()));
        LogHelper.info("DiskCacheInternal: " + mDiskCacheInternal.get("DiskCache"));
        LogHelper.info("DiskCacheExternal: " + mDiskCacheExternal.get("DiskCache"));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (navigationDrawer() != R.id.menu_recyclerView) {
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
            case R.id.menu_recyclerView:
            case R.id.menu_floatingActionButton:
            case R.id.menu_snackBar:
            case R.id.menu_transition:
            case R.id.menu_inputs:
                navigationDrawer(item.getItemId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected SkeletonFragment getFragment(final int itemId) {
        switch (itemId) {
            case R.id.menu_main:
                return new MainFragment();
            case R.id.menu_viewPagerTextIndicator:
                return new ViewPagerTextIndicatorFragment();
            case R.id.menu_viewPagerIconIndicator:
                return new ViewPagerIconIndicatorFragment();
            case R.id.menu_network:
                return new NetworkFragment();
            case R.id.menu_recyclerView:
                return new RecyclerViewFragment();
            case R.id.menu_floatingActionButton:
                return new FloatingActionButtonFragment();
            case R.id.menu_snackBar:
                return new SnackBarFragment();
            case R.id.menu_transition:
                return new TransitionFragment();
            case R.id.menu_inputs:
                return new InputsFragment();
            default:
                return null;
        }
    }

    @Override
    public boolean navigationDrawer(final int itemId) {
        if (super.navigationDrawer(itemId)) {
            subtitle(getFragment(itemId).title());
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // final boolean granted = PermissionsHelper.granted(PermissionsHelper.READ_PHONE_STATE, permissions, grantResults);
        // ActivityHelper.toast("READ_PHONE_STATE: " + (granted ? "GRANTED" : "DENIED"));
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
