package me.shkschneider.skeleton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.shkschneider.skeleton.helper.LogHelper;

public abstract class SkeletonNavigationDrawerActivity extends SkeletonActivity {

    protected ActionBarDrawerToggle mDrawerToggle;
    protected boolean mOpenedOrOpening = false;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected int mItemId;

    protected abstract SkeletonFragment getFragment(final int itemId);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sk_activity_navigationdrawer);
        home(true);

        mItemId = -1;
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer_drawerLayout);
        if (mDrawerLayout == null) {
            LogHelper.warning("DrawerLayout was NULL");
            return ;
        }

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

        mNavigationView = (NavigationView) findViewById(R.id.navigationDrawer_navigationView);
        if (mNavigationView == null) {
            LogHelper.warning("NavigationView was NULL");
            return ;
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                navigationDrawer(menuItem.getItemId());
                return true;
            }

        });

        final MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        if (menuItem != null) {
            navigationDrawer(menuItem.getItemId());
        }
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

    public void navigationDrawer(final int itemId) {
        mItemId = itemId;

        // Clears any loading state and capability
        refreshable(false, null);

        // Switch Fragment
        final SkeletonFragment skeletonFragment = getFragment(mItemId);
        if (skeletonFragment == null) {
            LogHelper.warning("SkeletonFragment was NULL");
            return ;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigationDrawer_frameLayout, skeletonFragment)
                .commit();

        // Updates NavigationDrawer
        mNavigationView.getMenu().findItem(mItemId).setChecked(true);
        closeNavigationDrawer();
        onNavigationDrawerClosed();
    }

    public int navigationDrawer() {
        return mItemId;
    }

    @Deprecated
    public boolean navigationDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(mNavigationView);
    }

    public boolean navigationDrawerOpenedOrOpening() {
        return mOpenedOrOpening;
    }

    public void openNavigationDrawer() {
        mDrawerLayout.openDrawer(mNavigationView);
    }

    protected void onNavigationDrawerOpened() {
        mOpenedOrOpening = true;
        supportInvalidateOptionsMenu();
    }

    public void closeNavigationDrawer() {
        mDrawerLayout.closeDrawer(mNavigationView);
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

}
