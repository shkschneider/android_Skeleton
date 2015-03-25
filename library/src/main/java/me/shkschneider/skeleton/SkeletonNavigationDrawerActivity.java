package me.shkschneider.skeleton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SharedPreferencesHelper;

public abstract class SkeletonNavigationDrawerActivity extends SkeletonActivity {

    public static final String TAB = "tab";

    protected boolean mOpenedOrOpening = false;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected ListView mDrawerList;

    protected abstract ArrayAdapter getAdapter();
    protected abstract SkeletonFragment getFragment(final int position);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sk_activity_navigationdrawer);
        home(true);

        SharedPreferencesHelper.putPublic(TAB, String.valueOf(0));
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer_drawerLayout);
        if (mDrawerLayout == null) {
            LogHelper.warning("DrawerLayout was NULL");
            return ;
        }
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, android.R.string.ok, android.R.string.cancel) {
            @Override
            public void onDrawerOpened(final View view) {
                onNavigationDrawerOpened();
            }

            @Override
            public void onDrawerClosed(final View view) {
                onNavigationDrawerClosed();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.navigationDrawer_listView);
        mDrawerList.setAdapter(getAdapter());
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                navigationDrawer(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final int tab = Integer.valueOf(SharedPreferencesHelper.getPublic(TAB, String.valueOf(0)));
        navigationDrawer(tab);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Fix for lower APIs where the ic_navigation_drawer drawable was not showed
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Fix for lower APIs where the ic_navigation_drawer drawable was not showed
        mDrawerToggle.syncState();
    }

    public void navigationDrawer(final int position) {
        // Clears any loading state and capability
        refreshable(false, null);

        // Switch Fragment
        final SkeletonFragment skeletonFragment = getFragment(position);
        if (skeletonFragment == null) {
            LogHelper.warning("SkeletonFragment was NULL");
            return ;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigationDrawer_frameLayout, skeletonFragment)
                .commit();

        // Updates NavigationDrawer
        mDrawerList.setItemChecked(position, true);
        closeNavigationDrawer();
        onNavigationDrawerClosed();
    }

    public int navigationDrawer() {
        final int tab = mDrawerList.getCheckedItemPosition();
        // default value might be -1
        return (tab < 0 ? 0 : tab);
    }

    @Deprecated
    public boolean navigationDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(mDrawerList);
    }

    public boolean navigationDrawerOpenedOrOpening() {
        return mOpenedOrOpening;
    }

    public void openNavigationDrawer() {
        mDrawerLayout.openDrawer(mDrawerList);
    }

    protected void onNavigationDrawerOpened() {
        mOpenedOrOpening = true;
        supportInvalidateOptionsMenu();
    }

    public void closeNavigationDrawer() {
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    protected void onNavigationDrawerClosed() {
        mOpenedOrOpening = false;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mOpenedOrOpening) {
            // Hides menu when opened
            menu.clear();
            return true;
        }
        // Restores menu when closed
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (! navigationDrawerOpenedOrOpening()) {
                onNavigationDrawerOpened();
            }
            else {
                onNavigationDrawerClosed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNavigationDrawer();

        final int tab = navigationDrawer();
        SharedPreferencesHelper.putPublic(TAB, String.valueOf(tab));
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        final int tab = navigationDrawer();
        outState.putInt(TAB, tab);
        SharedPreferencesHelper.putPublic(TAB, String.valueOf(tab));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int tab = savedInstanceState.getInt(TAB, 0);
        navigationDrawer(tab);
    }

}
