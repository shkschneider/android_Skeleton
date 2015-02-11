package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ScrollView;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

public class SkeletonActivity extends ActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.swiperefreshlayout);
        mMySwipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.mySwipeRefreshLayout);
        // setContentView()
        home(false);
        title(true);

        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            init21();
        }
    }

    @SuppressWarnings("NewApi")
    @SuppressLint("NewApi")
    @TargetApi(AndroidHelper.API_21)
    private void init21() {
        final String name = ApplicationHelper.name();
        final Bitmap icon = ApplicationHelper.icon();
        final int color = getResources().getColor(R.color.primaryColor);
        final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(name, icon, color);
        setTaskDescription(taskDescription);
    }

    public View view() {
        return findViewById(android.R.id.content);
    }

    // Lifecycle

    private boolean mAlive = false;

    @Override
    protected void onResume() {
        super.onResume();

        mAlive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mAlive = false;
    }

    public boolean alive() {
        return mAlive;
    }

    // ActionBar

    public void home(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowHomeEnabled(b);
        actionBar.setDisplayHomeAsUpEnabled(b);
    }

    public void icon(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowHomeEnabled(b);
    }

    @Deprecated
    public void logo(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayUseLogoEnabled(b);
    }

    public void title(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowTitleEnabled(b);
    }

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private int mLoadingCount = 0;
    private MySwipeRefreshLayout mMySwipeRefreshLayout;

    @Override
    public void setContentView(final int layoutResID) {
        final View view = getLayoutInflater().inflate(layoutResID, mMySwipeRefreshLayout, false);
        setContentView(view);
    }

    @Override
    public void setContentView(final View view) {
        setContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        mMySwipeRefreshLayout.addView(view, params);
        mMySwipeRefreshLayout.setColorSchemeResources(R.color.primaryColor);
    }

    public boolean refreshable() {
        return mMySwipeRefreshLayout.isEnabled();
    }

    public void refreshable(final boolean b, final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        // Resets loading count to avoid side-effects upon re-loading
        loading(false);
        mMySwipeRefreshLayout.setEnabled(b);
        mMySwipeRefreshLayout.setRefreshing(false);
        mMySwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void swipeRefreshLayoutCompat(@NonNull final AbsListView absListView) {
        MySwipeRefreshLayout.absListViewCompat(mMySwipeRefreshLayout, absListView);
    }

    public void swipeRefreshLayoutCompat(@NonNull final ScrollView scrollView) {
        MySwipeRefreshLayout.scrollViewCompat(mMySwipeRefreshLayout, scrollView);
    }

    public boolean loading() {
        return (mLoadingCount > 0);
    }

    public void loading(final int i) {
        mLoadingCount += i;
        if (mLoadingCount < 0) {
            mLoadingCount = 0;
        }
        loading((mLoadingCount > 0));
    }

    public void loading(final boolean b) {
        if (! b) {
            // Resets loading count to avoid side-effects upon re-loading
            mLoadingCount = 0;
            mMySwipeRefreshLayout.setRefreshing(false);
        }
        else if (! mMySwipeRefreshLayout.isRefreshing()) {
            mMySwipeRefreshLayout.setRefreshing(true);
        }
    }

    // Search
    // <http://stackoverflow.com/questions/18438890>

    private String mSearchHint;
    private SearchCallback mSearchCallback = null;
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;

    public void searchable(final String hint, final SearchCallback searchCallback) {
        mSearchHint = hint;
        mSearchCallback = searchCallback;
        // AVOID supportInvalidateOptionsMenu()
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mSearchCallback == null) {
            return super.onCreateOptionsMenu(menu);
        }

        getMenuInflater().inflate(R.menu.search, menu);

        mSearchMenuItem = menu.findItem(R.id.menu_search);
        if (mSearchMenuItem == null) {
            LogHelper.warning("SearchMenuItem was NULL");
            return super.onCreateOptionsMenu(menu);
        }
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (mSearchView == null) {
            LogHelper.warning("SearchView was NULL");
            return super.onCreateOptionsMenu(menu);
        }
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setQueryHint(mSearchHint);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchCallback.onSearchTextChange("");
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String q) {
                stopSearch();
                mSearchCallback.onSearchTextSubmit(q);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String q) {
                mSearchCallback.onSearchTextChange(q);
                return true;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onHomeAsUpPressed();
            return true;
        }
        if (mSearchCallback == null) {
            return super.onOptionsItemSelected(item);
        }
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        if (searchView == null) {
            return false;
        }
        if (item.getItemId() == R.id.menu_search) {
            searchView.setIconified(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void stopSearch() {
        mSearchMenuItem.collapseActionView();
        KeyboardHelper.hide(mSearchView.getWindowToken());
        mSearchView.clearFocus();
    }

    public static interface SearchCallback {

        public void onSearchTextChange(final String q);
        public void onSearchTextSubmit(final String q);

    }

    // Navigation

    public void onHomeAsUpPressed() {
        startActivity(IntentHelper.home());
    }

    @Override
    public void onBackPressed() {
        if (mSearchView != null && ! mSearchView.isIconified()) {
            stopSearch();
        }
        else {
            finish();
        }
    }

}
