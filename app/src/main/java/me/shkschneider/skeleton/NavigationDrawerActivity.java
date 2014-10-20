package me.shkschneider.skeleton;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.LogHelper;

public abstract class NavigationDrawerActivity extends SkeletonActivity {

    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    // TODO FIXME ActionBarDrawerToggle deprecated
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    public void setTitle(final CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        home(true);

        // TOFO FIXME NavigationDrawer not visible on API-17
        mTitle = getResources().getString(R.string.skeleton);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_drawerlayout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // TODO FIXME ActionBarDrawerToggle deprecated
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, android.R.string.ok, android.R.string.cancel) {
            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.drawer_listview);
        mDrawerList.setAdapter(getAdapter());
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                navigationDrawer(position);
            }
        });
        if (savedInstanceState == null) {
            navigationDrawer(0);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Fix for lower APIs: the ic_navigation_drawer drawable was not showed
        mDrawerToggle.syncState();
    }

    protected abstract ArrayAdapter getAdapter();
    protected abstract Fragment getFragment(final int position);
    protected abstract String getTitle(final int position);

    public void navigationDrawer(final int position) {
        final Fragment fragment = getFragment(position);
        if (fragment == null) {
            LogHelper.warning("Fragment was NULL");
            return ;
        }

        loading(false);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_framelayout, fragment)
                .commit();
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        setTitle(getTitle(position));
        supportInvalidateOptionsMenu();
    }

    public int navigationDrawer() {
        return mDrawerList.getCheckedItemPosition();
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawerLayout.closeDrawer(mDrawerList);
    }

}
