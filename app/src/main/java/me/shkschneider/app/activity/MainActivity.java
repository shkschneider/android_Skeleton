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
import me.shkschneider.app.fragment.ViewPagerCircleIndicatorFragment;
import me.shkschneider.app.fragment.ViewPagerIconIndicatorFragment;
import me.shkschneider.app.fragment.ViewPagerTextIndicatorFragment;
import me.shkschneider.app.fragment.FloatingActionButtonFragment;
import me.shkschneider.app.fragment.MainFragment;
import me.shkschneider.app.fragment.NetworkFragment;
import me.shkschneider.skeleton.SkeletonNavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.data.DiskCacher;
import me.shkschneider.skeleton.data.MemoryCacher;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;

public class MainActivity extends SkeletonNavigationDrawerActivity {

    public static final int NAVIGATION_MAIN = 0;
    public static final int NAVIGATION_VIEWPAGERTEXTINDICATOR = 1;
    public static final int NAVIGATION_VIEWPAGERICONINDICATOR = 2;
    public static final int NAVIGATION_VIEWPAGERCIRCLEINDICATOR = 3;
    public static final int NAVIGATION_NETWORK = 4;
    public static final int NAVIGATION_LISTVIEW = 5;
    public static final int NAVIGATION_FLOATINGACTIONBUTTON = 6;
    public static final int NAVIGATION_SNACKBAR = 7;

    private MemoryCacher mMemoryCacher;
    private DiskCacher.Internal mDiskCacherInternal;
    private DiskCacher.External mDiskCacherExternal;

    public static Intent getIntent(final Activity activity) {
        return new Intent(activity, MainActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationDrawer(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMemoryCacher = new MemoryCacher();
        mMemoryCacher.put("MainActivity", MainActivity.this);
        mDiskCacherInternal = new DiskCacher.Internal();
        mDiskCacherInternal.put("DiskCacher", "Internal");
        mDiskCacherExternal = new DiskCacher.External();
        mDiskCacherExternal.put("DiskCacher", "External");
        ActivityHelper.toast(mMemoryCacher.get("MainActivity").toString());
        ActivityHelper.toast(mDiskCacherInternal.get("DiskCacher").toString());
        ActivityHelper.toast(mDiskCacherExternal.get("DiskCacher").toString());
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMemoryCacher.clear();
        mMemoryCacher = null;
        mDiskCacherInternal.clear();
        mDiskCacherInternal = null;
        mDiskCacherExternal.clear();
        mDiskCacherExternal = null;
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
            }
        }) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                    convertView = layoutInflater.inflate(R.layout.listview_navigationdrawer_item, parent, false);
                }
                final TextView textView = ((TextView) convertView.findViewById(R.id.textview));
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

}
