package me.sdk;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import me.app.R;

/**
 * boolean alive()
 * void home(boolean)
 * void searchable(String, SearchCallback)
 * boolean charging()
 * void charging(int)
 * boolean loading()
 * void loading(boolean)
 */
public class MyActivity extends ActionBarActivity {

    private boolean mAlive = false;
    private String mSearchHint;
    private SearchCallback mSearchCallback = null; // Activated if not null
    private MenuItem mSearchMenuItem;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        // setContentView()
    }

    private void setContentView() {
        mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        mProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 24));
        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);

        final FrameLayout frameLayout = (FrameLayout) getWindow().getDecorView();
        frameLayout.addView(mProgressBar);

        final ViewTreeObserver viewTreeObserver = mProgressBar.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final View view = frameLayout.findViewById(android.R.id.content);
                mProgressBar.setY(view.getY() - 10);
                mProgressBar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                if (AndroidHelper.api() < AndroidHelper.API_16) {
                    removeGlobalLayoutListenerOld(mProgressBar.getViewTreeObserver(), this);
                }
                else {
                    removeGlobalLayoutListenerNew(mProgressBar.getViewTreeObserver(), this);
                }
            }

            @TargetApi(AndroidHelper.API_16)
            private void removeGlobalLayoutListenerNew(final ViewTreeObserver viewTreeObserver, final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener) {
                viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener);
            }

            @SuppressWarnings("deprecation")
            private void removeGlobalLayoutListenerOld(final ViewTreeObserver viewTreeObserver, final ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener) {
                viewTreeObserver.removeGlobalOnLayoutListener(globalLayoutListener);
            }
        });
    }

    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);
        setContentView();
    }

    @Override
    public void setContentView(final View view) {
        super.setContentView(view);
        setContentView();
    }

    @Deprecated
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setContentView();
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

    public void home(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }

        actionBar.setDisplayHomeAsUpEnabled(b);
    }

    public void searchable(final String hint, final SearchCallback searchCallback) {
        mSearchHint = hint;
        mSearchCallback = searchCallback;
        supportInvalidateOptionsMenu();
    }

    public boolean charging() {
        return (! loading() && mProgressBar.getVisibility() == View.VISIBLE);
    }

    public void charging(int percent) {
        mProgressBar.setIndeterminate(false);
        if (percent <= 0) {
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.GONE);
            return ;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        if (percent > 100) {
            percent = 100;
        }
        mProgressBar.setProgress(percent);
    }

    public boolean loading() {
        return mProgressBar.isIndeterminate();
    }

    public void loading(final boolean b) {
        mProgressBar.setIndeterminate(b);
        mProgressBar.setVisibility((b ? View.VISIBLE : View.GONE));
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

    private void stopSearch() {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (searchView == null) {
            return ;
        }

        searchView.clearFocus();
        mSearchMenuItem.collapseActionView();
        KeyboardHelper.hide(searchView.getWindowToken());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static interface SearchCallback {

        public void onSearchTextChange(final String q);
        public void onSearchTextSubmit(final String q);

    }

}
