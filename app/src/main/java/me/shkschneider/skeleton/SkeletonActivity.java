package me.shkschneider.skeleton;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.StringHelper;

/**
 * boolean alive()
 * void home(boolean)
 * void searchable(String, SearchCallback)
 * boolean charging()
 * void charging(int)
 * boolean loading()
 * void loading(boolean)
 */
public class SkeletonActivity extends ActionBarActivity {

    private boolean mAlive = false;
    private ActionMode mActionMode = null;
    private String mSearchHint;
    private SearchCallback mSearchCallback = null; // Activated if not null
    private MenuItem mSearchMenuItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        actionBar.setDisplayShowHomeEnabled(b);
    }

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
            try {
                final Drawable icon = getPackageManager().getActivityIcon(getComponentName());
                actionBar.setIcon(icon);
            }
            catch (final Exception e) {
                LogHelper.wtf(e);
            }
        }
    }

    public void title(final String title) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }

        if (StringHelper.nullOrEmpty(title)) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setTitle(null);
        }
        else {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
        }
    }

    public void searchable(final String hint, final SearchCallback searchCallback) {
        mSearchHint = hint;
        mSearchCallback = searchCallback;
        // supportInvalidateOptionsMenu();
    }

    public boolean loading() {
        return (mActionMode != null);
    }

    public void loading(final boolean b) {
        if (b) {
            mActionMode = startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(final ActionMode actionMode, final Menu menu) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(SkeletonActivity.this);
                    final View view = layoutInflater.inflate(R.layout.loading, null);
                    ((TextView) view.findViewById(R.id.title)).setText(getSupportActionBar().getTitle());
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
                    // Ignore
                }
            });
        }
        else if (mActionMode != null) {
            mActionMode.finish();
            mActionMode = null;
        }
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO FIXME HomeAsUp does not cancel search
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchCallback.onSearchTextChange("");
                return false;
            }
        });
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
            final Intent intent = getPackageManager().getLaunchIntentForPackage(ApplicationHelper.packageName());
            startActivity(intent.setFlags(IntentHelper.HOME_FLAGS));
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

    public static interface NavigationCallback {

        public void onHomeAsUpPressed();
        public void onBackPressed();

    }

}
