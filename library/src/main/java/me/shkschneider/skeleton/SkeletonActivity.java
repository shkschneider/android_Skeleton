package me.shkschneider.skeleton;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout;

/**
 * https://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 * https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html
 *
 *     onCreated()
 *     onNewIntent()
 *     onViewCreated()
 *     onStart()
 *     onResume()
 *     onResumeFragments()
 *     onPause()
 *     onStop()
 *     onDestroy()
 */
public abstract class SkeletonActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected MySwipeRefreshLayout mMySwipeRefreshLayout;

    private boolean mAlive = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.sk_activity);

        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            init21();
        }

        final Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            LogHelper.verbose("onNewIntent: " + intent);
            onNewIntent(intent);
        }
    }

    @TargetApi(AndroidHelper.API_21)
    private void init21() {
        statusBarColor(getWindow(), getResources().getColor(R.color.statusBarColor));

        final String name = ApplicationHelper.name();
        final Bitmap icon = ApplicationHelper.icon();
        final int color = getResources().getColor(R.color.primaryColor);
        final ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(name, icon, color);
        setTaskDescription(taskDescription);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public void setContentView(@LayoutRes final int id) {
        super.setContentView(id);
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
    public boolean statusBarColor(@NonNull final Window window, @ColorInt final int color) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            return true;
        }
        return false;
    }

    public boolean toolbarColor(@ColorInt final int color) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL");
            return false;
        }
        mToolbar.setBackgroundColor(color);
        return true;
    }

    protected void bindToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            // LogHelper.verbose("Found a Toolbar");
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
            // LogHelper.verbose("Found a MySwipeRefreshLayout");
            mMySwipeRefreshLayout.setColorSchemeResources(R.color.primaryColor);
        }
    }

    protected void onViewCreated() {
        // Override
    }

    // Lifecycle

    @Override
    protected void onResume() {
        super.onResume();

        mAlive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSkeletonReceiver = null;
        mAlive = false;
        loading(false);
    }

    public boolean alive() {
        return mAlive;
    }

    // ToolBar

    public Toolbar toolbar() {
        return mToolbar;
    }

    public void toolbar(final boolean b) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL");
            return;
        }
        final int visibility = (b ? View.VISIBLE : View.GONE);
        mToolbar.setVisibility(visibility);
    }

    public void home(final boolean b) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(b);
    }

    @Deprecated // Avoid
    public void home(final Drawable drawable) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL");
            return;
        }
        mToolbar.setNavigationIcon(drawable);
    }

    @Deprecated
    public void icon(final Drawable drawable) {
        logo(drawable);
    }

    public void logo(final Drawable drawable) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return;
        }
        actionBar.setDisplayShowHomeEnabled((drawable != null));
        actionBar.setDisplayUseLogoEnabled((drawable != null));
        actionBar.setLogo(drawable);
    }

    public void title(final String title) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return;
        }
        actionBar.setDisplayShowTitleEnabled(! TextUtils.isEmpty(title));
        actionBar.setTitle(title);
    }

    public String title() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return null;
        }
        final CharSequence title = actionBar.getTitle();
        return ((title != null) ? title.toString() : null);
    }

    public void subtitle(final String subtitle) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return;
        }
        actionBar.setDisplayShowTitleEnabled(! TextUtils.isEmpty(subtitle));
        actionBar.setSubtitle(subtitle);
    }

    public String subtitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL");
            return null;
        }
        final CharSequence subtitle = actionBar.getSubtitle();
        return ((subtitle != null) ? subtitle.toString() : null);
    }

    // MySwipeRefreshLayout

    public MySwipeRefreshLayout mySwipeRefreshLayout() {
        return mMySwipeRefreshLayout;
    }

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private int mLoading;

    public boolean refreshable() {
        if (mMySwipeRefreshLayout == null) {
            return false;
        }
        return mMySwipeRefreshLayout.isEnabled();
    }

    public void refreshable(final boolean b, @Nullable final SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        // Resets loading count to avoid side-effects upon re-loading
        mLoading = 0;
        if (! b) {
            mMySwipeRefreshLayout = null;
        }
        else {
            if (mMySwipeRefreshLayout == null) {
                LogHelper.warning("MySwipeRefreshLayout was NULL");
                return;
            }
            mMySwipeRefreshLayout.setRefreshing(false);
            mMySwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    public int loading() {
        return mLoading;
    }

    public void loading(final int i) {
        int count = mLoading + i;
        if (count < 0) {
            loading(false);
            mLoading = 0;
            return;
        }
        if (mLoading == 0 && count > 0) {
            loading(true);
        }
        else if (mLoading > 0 && count == 0) {
            loading(false);
        }
        mLoading = count;
    }

    public void loading(final boolean b) {
        if (! b) {
            // Resets loading count to avoid side-effects upon re-loading
            mLoading = 0;
        }
        if (mMySwipeRefreshLayout == null) {
            if (b) {
                LogHelper.warning("MySwipeRefreshLayout was NULL");
            }
            return;
        }
        if (! b) {
            mMySwipeRefreshLayout.setRefreshing(false);
            mMySwipeRefreshLayout.destroyDrawingCache();
            mMySwipeRefreshLayout.clearAnimation();
        }
        else if (! mMySwipeRefreshLayout.isRefreshing()) {
            mMySwipeRefreshLayout.setRefreshing(true);
        }
    }

    // Search
    // <http://stackoverflow.com/questions/18438890>

    protected static final int RESULT_SEARCH_CHANGE = -1;
    protected static final int RESULT_SEARCH_SUBMIT = 1;
    protected static final String RESULT_SEARCH_TEXT = "TEXT";

    private String mSearchHint;
    private SkeletonReceiver mSkeletonReceiver;
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;

    public boolean searchable() {
        return (mSkeletonReceiver != null);
    }

    public void searchable(final String hint, @Nullable final SkeletonReceiver skeletonReceiver) {
        mSearchHint = hint;
        mSkeletonReceiver = skeletonReceiver;
        // AVOID supportInvalidateOptionsMenu()
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mSkeletonReceiver == null) {
            return super.onCreateOptionsMenu(menu);
        }

        getMenuInflater().inflate(R.menu.sk_search, menu);

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
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setQueryHint(mSearchHint);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                final Bundle bundle = new Bundle();
                bundle.putString(RESULT_SEARCH_TEXT, "");
                mSkeletonReceiver.send(RESULT_SEARCH_CHANGE, bundle);
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String q) {
                stopSearch();
                final Bundle bundle = new Bundle();
                bundle.putString(RESULT_SEARCH_TEXT, q);
                mSkeletonReceiver.send(RESULT_SEARCH_SUBMIT, bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String q) {
                final Bundle bundle = new Bundle();
                bundle.putString(RESULT_SEARCH_TEXT, q);
                mSkeletonReceiver.send(RESULT_SEARCH_CHANGE, bundle);
                return true;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mSkeletonReceiver == null) {
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
        KeyboardHelper.hide(getWindow());
        mSearchView.clearFocus();
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

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // BundleHelper.pack()
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // BundleHelper.unpack()
    }

    // Runtime Permissions

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
