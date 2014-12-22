package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.ApplicationHelper;
import me.shkschneider.skeleton.helpers.KeyboardHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.ui.ImageHelper;

public class SkeletonActivity extends ActionBarActivity {

    private int mLoadingCount = 0;
    private TextView mLoadingView;
    private boolean mAlive = false;
    private ActionMode mActionMode = null;
    private String mSearchHint;
    private SearchCallback mSearchCallback; // Activated if not null
    private MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setElevation(0F);
        }

        final String name = ApplicationHelper.name();
        final Bitmap icon = ApplicationHelper.icon();
        final int color = getResources().getColor(R.color.primaryColor);
        final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(name, icon, color);
        setTaskDescription(taskDescription);
    }

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

    public void actionbar(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }

        if (b) {
            actionBar.show();
        }
        else {
            actionBar.hide();
        }
    }

    public void home(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowHomeEnabled(b);
        actionBar.setDisplayHomeAsUpEnabled(b);
    }

    @Deprecated
    public void logo(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayUseLogoEnabled(b);
        if (! b) {
            actionBar.setIcon(getResources().getColor(android.R.color.transparent));
        }
        else {
            actionBar.setIcon(ImageHelper.fromBitmap(ApplicationHelper.icon()));
        }
    }

    public void title(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
        actionBar.setDisplayShowTitleEnabled(b);
    }

    public void searchable(final String hint, final SearchCallback searchCallback) {
        mSearchHint = hint;
        mSearchCallback = searchCallback;
        // AVOID supportInvalidateOptionsMenu();
    }

    public void searchable(final SearchCallback searchCallback) {
        searchable(null, searchCallback);
    }

    // Loading

    public boolean loading() {
        // Checking ActionMode is more reliable as mLoadingCount
        return (mActionMode != null);
    }

    // Increments or decrements the loading stack
    public void loading(final int i) {
        mLoadingCount += i;
        updateLoading(ApplicationHelper.debug());
    }

    // Resets loading to true or false
    public void indeterminateLoading(final boolean b) {
        mLoadingCount = (b ? 1 : 0);
        updateLoading(false);
    }

    public int loadingCount() {
        return mLoadingCount;
    }

    private void updateLoading(final boolean showCount) {
        // Just to be safe: can happen with loading(-1) after an indeterminateLoading(false)
        if (mLoadingCount < 0) {
            mLoadingCount = 0;
        }
        if (mLoadingCount == 0) {
            if (mActionMode != null) {
                mActionMode.finish();
                mActionMode = null;
                mLoadingView.setText("");
                mLoadingView = null;
            }
            return;
        }
        final boolean actionBarVisible = (getSupportActionBar() != null && getSupportActionBar().isShowing());
        if (actionBarVisible && !loading()) {
            mActionMode = startSupportActionMode(new ActionMode.Callback() {
                @SuppressLint("InflateParams") // inflater has no ViewGroup
                @Override
                public boolean onCreateActionMode(final ActionMode actionMode, final Menu menu) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(SkeletonActivity.this);
                    final View view = layoutInflater.inflate(R.layout.actionmode_loading, null);
                    ((TextView) view.findViewById(R.id.title)).setText(getSupportActionBar().getTitle());
                    mLoadingView = (TextView) view.findViewById(R.id.loading);
                    actionMode.setCustomView(view);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(final ActionMode actionMode, final Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(final ActionMode actionMode, final MenuItem menuItem) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(final ActionMode actionMode) {
                    stopLoading();
                }
            });
        }
        if (showCount) {
            mLoadingView.setText(String.valueOf(mLoadingCount));
        }
        else {
            mLoadingView.setText("");
        }
    }

    protected void stopLoading() {
        // cancels all network calls
        Ion.getDefault(this).cancelAll();
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

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (searchView == null) {
            LogHelper.warning("SearchView was NULL");
            return super.onCreateOptionsMenu(menu);
        }

        // SearchViewFormatter: <https://gist.github.com/ademar111190/7d31dab71502e6a85b8a>

        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(mSearchHint);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String q) {
                stopSearch();
                searchView.clearFocus();
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

    public void stopSearch() {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (searchView == null) {
            return ;
        }

        searchView.clearFocus();
        mSearchMenuItem.collapseActionView();
        KeyboardHelper.hide(searchView.getWindowToken());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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

    @Override
    public void onBackPressed() {
        finish();
    }

    public void loading(final boolean b) {
        setSupportProgressBarIndeterminateVisibility(b);
        setSupportProgressBarIndeterminate(b);
    }

    public static interface SearchCallback {

        public void onSearchTextChange(final String q);
        public void onSearchTextSubmit(final String q);

    }

    public static interface NavigationCallback {

        public void onHomeAsUpPressed();
        public void onBackPressed();

    }

}
