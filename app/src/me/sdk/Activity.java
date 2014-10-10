package me.sdk;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import me.app.R;

/**
 * boolean alive()
 * void home(boolean)
 * void searchable(String, SearchCallback)
 * void loading(boolean)
 */
public class Activity extends ActionBarActivity {

    private boolean mAlive = false;
    private String mSearchHint;
    private SearchCallback mSearchCallback = null; // Activated if not null
    private MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // DEPRECATED
        // setContentView()
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

    @Deprecated
    public void loading(final boolean b) {
        setSupportProgressBarIndeterminateVisibility(b);
        setSupportProgressBarIndeterminate(b);
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
