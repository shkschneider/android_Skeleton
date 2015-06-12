package me.shkschneider.skeleton;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ScrollView;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.Log;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

public class SkeletonActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected MySwipeRefreshLayout mMySwipeRefreshLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sk_activity);
        statusBarColor(getWindow(), getResources().getColor(R.color.statusBarColor));

        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            init21();
        }
    }

    @TargetApi(AndroidHelper.API_21)
    private void init21() {
        final String name = ApplicationHelper.name();
        final Bitmap icon = ApplicationHelper.icon();
        final int color = getResources().getColor(R.color.primaryColor);
        final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(name, icon, color);
        setTaskDescription(taskDescription);
    }

    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
        onViewCreated();
    }

    @Override
    public void setContentView(final View view) {
        super.setContentView(view);
        bindViews();
        onViewCreated();
    }

    @Override
    public void setContentView(final View view, final ViewGroup.LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
        bindViews();
        onViewCreated();
    }

    private void bindViews() {
        bindToolbar();
        bindMySwipeRefreshLayout();
    }

    @TargetApi(AndroidHelper.API_21)
    public int statusBarColor(@NonNull final Window window) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            return window.getStatusBarColor();
        }
        return Color.TRANSPARENT;
    }

    @TargetApi(AndroidHelper.API_21)
    public boolean statusBarColor(@NonNull final Window window, final int color) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            return true;
        }
        return false;
    }

    public boolean actionBarColor(final int color) {
        if (mToolbar == null) {
            Log.w("Toolbar was NULL");
            return false;
        }
        mToolbar.setBackgroundColor(color);
        return true;
    }

    public boolean actionBarDrawable(final Drawable drawable) {
        if (AndroidHelper.api() >= AndroidHelper.API_16) {
            return actionBarDrawable16(drawable);
        }
        else {
            return actionBarDrawable1(drawable);
        }
    }

    @SuppressWarnings("deprecation")
    private boolean actionBarDrawable1(final Drawable drawable) {
        if (mToolbar == null) {
            Log.w("Toolbar was NULL");
            return false;
        }
        mToolbar.setBackgroundDrawable(drawable);
        return true;
    }

    @TargetApi(AndroidHelper.API_16)
    private boolean actionBarDrawable16(final Drawable drawable) {
        if (mToolbar == null) {
            Log.w("Toolbar was NULL");
            return false;
        }
        mToolbar.setBackground(drawable);
        return true;
    }

    protected void bindToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            Log.v("Found a Toolbar");
            setSupportActionBar(mToolbar);
            title(ApplicationHelper.name());
        }
    }

    @Override
    public void setSupportActionBar(final Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        mToolbar = toolbar;
    }

    protected void bindMySwipeRefreshLayout() {
        mMySwipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.mySwipeRefreshLayout);
        if (mMySwipeRefreshLayout != null) {
            Log.v("Found a MySwipeRefreshLayout");
            mMySwipeRefreshLayout.setColorSchemeResources(R.color.primaryColor);
        }
    }

    protected void onViewCreated() {
        // Override
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
        loading(false);
    }

    public boolean alive() {
        return mAlive;
    }

    // ToolBar

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void toolbar(final boolean b) {
        if (mToolbar == null) {
            Log.w("Toolbar was NULL");
            return ;
        }
        final int visibility = (b ? View.VISIBLE : View.GONE);
        mToolbar.setVisibility(visibility);
    }

    public void home(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.w("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowHomeEnabled(b);
        actionBar.setDisplayHomeAsUpEnabled(b);
    }

    public void home(final Drawable drawable) {
        if (mToolbar == null) {
            Log.w("Toolbar was NULL");
            return ;
        }
        mToolbar.setNavigationIcon(drawable);
    }

    public void title(final String title) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.w("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowTitleEnabled(! StringHelper.nullOrEmpty(title));
        actionBar.setTitle(title);
    }

    public void subtitle(final String subtitle) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.w("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowTitleEnabled(! StringHelper.nullOrEmpty(subtitle));
        actionBar.setSubtitle(subtitle);
    }

    public void logo(final Drawable drawable) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.w("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayUseLogoEnabled(! (drawable == null));
        actionBar.setLogo(drawable);
    }

    @Deprecated
    public void icon(final Drawable drawable) {
        logo(drawable);
    }

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private int mLoadingCount;

    public boolean refreshable() {
        if (mMySwipeRefreshLayout == null) {
            return false;
        }
        return mMySwipeRefreshLayout.isEnabled();
    }

    public void refreshable(final boolean b, final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        // Resets loading count to avoid side-effects upon re-loading
        mLoadingCount = 0;
        if (mMySwipeRefreshLayout == null) {
            if (b) {
                Log.w("MySwipeRefreshLayout was NULL");
            }
            return ;
        }
        mMySwipeRefreshLayout.setEnabled(b);
        mMySwipeRefreshLayout.setRefreshing(false);
        mMySwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    public void swipeRefreshLayoutCompat(@NonNull final AbsListView absListView) {
        if (mMySwipeRefreshLayout == null) {
            Log.w("MySwipeRefreshLayout was NULL");
            return ;
        }
        MySwipeRefreshLayout.absListViewCompat(mMySwipeRefreshLayout, absListView);
    }

    public void swipeRefreshLayoutCompat(@NonNull final ScrollView scrollView) {
        if (mMySwipeRefreshLayout == null) {
            Log.w("MySwipeRefreshLayout was NULL");
            return ;
        }
        MySwipeRefreshLayout.scrollViewCompat(mMySwipeRefreshLayout, scrollView);
    }

    public boolean loading() {
        return (mLoadingCount > 0);
    }

    public int loadingCount() {
        return mLoadingCount;
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
        }
        if (mMySwipeRefreshLayout == null) {
            if (b) {
                Log.w("MySwipeRefreshLayout was NULL");
            }
            return ;
        }
        if (! b) {
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

        getMenuInflater().inflate(R.menu.sk_search, menu);

        mSearchMenuItem = menu.findItem(R.id.menu_search);
        if (mSearchMenuItem == null) {
            Log.w("SearchMenuItem was NULL");
            return super.onCreateOptionsMenu(menu);
        }
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (mSearchView == null) {
            Log.w("SearchView was NULL");
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
        KeyboardHelper.hide(this);
        mSearchView.clearFocus();
    }

    public interface SearchCallback {

        void onSearchTextChange(final String q);
        void onSearchTextSubmit(final String q);

    }

    // Navigation


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(IntentHelper.home());
        return true;
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
