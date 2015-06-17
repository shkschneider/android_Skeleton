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
import me.shkschneider.skeleton.helper.Log;

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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.sk_drawerLayout);
        if (mDrawerLayout == null) {
            Log.w("DrawerLayout was NULL");
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

        mNavigationView = (NavigationView) findViewById(R.id.sk_navigationView);
        if (mNavigationView == null) {
            Log.w("NavigationView was NULL");
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

        final MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        if (menuItem != null) {
            navigationDrawer(menuItem.getItemId());
        }
    }

    public boolean navigationDrawer(final int itemId) {
        // <https://stackoverflow.com/q/30752713/>
        if (! alive() || transitioning()) {
            Log.w("Switching Fragments too early");
            return false;
        }
        transitioning(true);

        // Clears any loading state and capability
        refreshable(false, null);

        // Switch Fragment
        final SkeletonFragment skeletonFragment = getFragment(itemId);
        if (skeletonFragment == null) {
            Log.w("SkeletonFragment was NULL");
            transitioning(false);
            return false;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sk_frameLayout, skeletonFragment)
                .addToBackStack(null)
                .commit();

        // Updates NavigationDrawer
        final MenuItem menuItem = mNavigationView.getMenu().findItem(itemId);
        if (menuItem != null) {
            menuItem.setChecked(true);
        }
        closeNavigationDrawer();
        onNavigationDrawerClosed();

        return true;
    }

    public int navigationDrawer() {
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            final MenuItem menuItem = mNavigationView.getMenu().getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return -1;
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
