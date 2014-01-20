package me.shkschneider.skeleton;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.maximiles.nfc.R;

import me.shkschneider.skeleton.helpers.AndroidHelper;
import me.shkschneider.skeleton.helpers.KeyboardHelper;
import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.SystemHelper;

@SuppressWarnings("unused")
public class SkeletonActivity extends ActionBarActivity {

    // Also acts as a boolean: activated if not null
    private SearchCallback mSearchCallback;
    private MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        // You need to extend this class and call setContentView() yourself
    }

    public void home() {
        getSupportActionBar().setHomeButtonEnabled(true); // enables
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows arrow
    }

    public boolean makeSearchable(final SearchCallback searchCallback) {
        if (searchCallback == null) {
            LogHelper.warning("SearchCallback was NULL");
            return false;
        }

        mSearchCallback = searchCallback;
        supportInvalidateOptionsMenu();
        return true;
    }

    public boolean makeNotSearchable() {
        if (mSearchCallback == null) {
            LogHelper.warning("SearchCallback was NULL");
            return false;
        }

        mSearchCallback = null;
        supportInvalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mSearchCallback == null) {
            return super.onCreateOptionsMenu(menu);
        }

        getMenuInflater().inflate(R.menu.search, menu);

        final SearchManager searchManager = (SearchManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_SEARCH_SERVICE);
        mSearchMenuItem = menu.findItem(R.id.menu_search);
        if (mSearchMenuItem == null) {
            return super.onCreateOptionsMenu(menu);
        }

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (searchView == null) {
            return super.onCreateOptionsMenu(menu);
        }

        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getResources().getString(R.string.dots));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String q) {
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

    @TargetApi(AndroidHelper.API_14)
    private void stopSearchNew(final SearchView searchView) {
        searchView.clearFocus();
        mSearchMenuItem.collapseActionView();
        KeyboardHelper.hide(searchView.getWindowToken());
    }

    private void stopSearchOld(final SearchView searchView) {
        searchView.clearFocus();
        KeyboardHelper.hide(searchView.getWindowToken());
    }

    public void stopSearch() {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        if (searchView == null) {
            return ;
        }

        if (AndroidHelper.api() >= AndroidHelper.API_14) {
            stopSearchNew(searchView);
        }
        else {
            stopSearchOld(searchView);
        }
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
        setProgressBarIndeterminateVisibility(b);
    }

    public static interface SearchCallback {

        public void onSearchTextChange(final String q);
        public void onSearchTextSubmit(final String q);

    }

}
