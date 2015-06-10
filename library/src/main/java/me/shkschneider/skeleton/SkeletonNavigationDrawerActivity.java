package me.shkschneider.skeleton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public abstract class SkeletonNavigationDrawerActivity extends SkeletonActivity {

    protected ActionBarDrawerToggle mDrawerToggle;
    protected boolean mOpenedOrOpening = false;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;

    protected abstract SkeletonFragment getFragment(final int itemId);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sk_navigationdraweractivity);
        home(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer_drawerLayout);
        if (mDrawerLayout == null) {
            LogHelper.warning("DrawerLayout was NULL");
            return ;
        }

        mDrawerToggle = new ActionBarDrawerToggle(SkeletonNavigationDrawerActivity.this, mDrawerLayout, android.R.string.ok, android.R.string.cancel) {

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
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            mNavigationView.setPadding(0, (int) getResources().getDimension(R.dimen.statusBar), 0, 0);
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                navigationDrawer(menuItem.getItemId());
                return true;
            }

        });

        if (savedInstanceState == null) {
            final MenuItem menuItem = mNavigationView.getMenu().getItem(0);
            if (menuItem != null) {
                navigationDrawer(menuItem.getItemId());
            }
        }
    }

    public void setNavigationHeader(@LayoutRes final int id) {
        mNavigationView.inflateHeaderView(id);
    }

    public void setNavigationMenu(@MenuRes final int id) {
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(id);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mDrawerToggle.syncState();
    }

    public void navigationDrawer(final int itemId) {
        // Clears any loading state and capability
        refreshable(false, null);

        // Switch Fragment
        final SkeletonFragment skeletonFragment = getFragment(itemId);
        if (skeletonFragment == null) {
            LogHelper.warning("SkeletonFragment was NULL");
            return ;
        }

        // FIXME overlap
        getSupportFragmentManager().executePendingTransactions();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigationDrawer_frameLayout, skeletonFragment)
                .addToBackStack(null)
                .commit();

        // Updates NavigationDrawer
        mNavigationView.getMenu().findItem(itemId).setChecked(true);
        closeNavigationDrawer();
        onNavigationDrawerClosed();
    }

    public int navigationDrawer() {
        return mNavigationView.getMenu().getItem(0).getItemId();
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
        KeyboardHelper.hide(SkeletonNavigationDrawerActivity.this);
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

    @Override
    public void onBackPressed() {
        if (mOpenedOrOpening) {
            closeNavigationDrawer();
        }
        else {
            super.onBackPressed();
        }
    }

}
