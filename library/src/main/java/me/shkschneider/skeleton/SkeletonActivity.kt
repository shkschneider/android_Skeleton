package me.shkschneider.skeleton

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo

import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.KeyboardHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.ui.MySwipeRefreshLayout

/**
 * https://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 * https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html
 *
 * onCreate()
 * onAttachFragments()
 * onContentChanged()
 * onStart()
 * onRestoreInstanceState()
 * onPostCreate()
 * onViewCreate()
 * onResume()
 * onPostResume()
 * onAttachedToWindow()
 * onCreateOptionsMenu()
 * onPrepareOptionsMenu()
 * onPause()
 * onSaveInstanceState()
 * onStop()
 * onDestroy()
 */
abstract class SkeletonActivity : AppCompatActivity() {

    protected var mToolbar: Toolbar? = null
    protected var mMySwipeRefreshLayout: MySwipeRefreshLayout? = null

    private var mAlive = false

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private var mLoading: Int = 0

    private var mSearchHint: String? = null
    private var mSkeletonReceiver: SkeletonReceiver? = null
    private var mSearchMenuItem: MenuItem? = null
    private var mSearchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.sk_activity)

        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            init21()
        }

        val intent = intent
        if (intent != null && intent.extras != null) {
            LogHelper.verbose("onNewIntent: " + intent)
            onNewIntent(intent)
        }
    }

    @TargetApi(AndroidHelper.API_21)
    private fun init21() {
        statusBarColor(window, ContextCompat.getColor(applicationContext, R.color.statusBarColor))

        val name = ApplicationHelper.name()
        val icon = ApplicationHelper.icon()
        val color = ContextCompat.getColor(applicationContext, R.color.primaryColor)
        val taskDescription = ActivityManager.TaskDescription(name, icon, color)
        setTaskDescription(taskDescription)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
    }

    override fun setContentView(@LayoutRes id: Int) {
        super.setContentView(id)
        bindViews()
        onViewCreated()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        bindViews()
        onViewCreated()
    }

    override fun setContentView(view: View, layoutParams: ViewGroup.LayoutParams) {
        super.setContentView(view, layoutParams)
        bindViews()
        onViewCreated()
    }

    private fun bindViews() {
        bindToolbar()
        bindMySwipeRefreshLayout()
    }

    @TargetApi(AndroidHelper.API_21)
    fun statusBarColor(window: Window): Int {
        return if (AndroidHelper.api() >= AndroidHelper.API_21) {
            window.statusBarColor
        } else Color.TRANSPARENT
    }

    @TargetApi(AndroidHelper.API_21)
    fun statusBarColor(window: Window, @ColorInt color: Int): Boolean {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            return true
        }
        return false
    }

    fun toolbarColor(@ColorInt color: Int): Boolean {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return false
        }
        mToolbar!!.setBackgroundColor(color)
        return true
    }

    protected fun bindToolbar() {
        mToolbar = findViewById(R.id.toolbar)
        if (mToolbar != null) {
            // LogHelper.verbose("Found a Toolbar");
            setSupportActionBar(mToolbar)
            title(ApplicationHelper.name())
        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        mToolbar = toolbar
    }

    fun bindMySwipeRefreshLayout() {
        mMySwipeRefreshLayout = findViewById(R.id.mySwipeRefreshLayout)
        if (mMySwipeRefreshLayout != null) {
            // LogHelper.verbose("Found a MySwipeRefreshLayout");
            mMySwipeRefreshLayout!!.setColorSchemeResources(R.color.primaryColor)
        }
    }

    protected fun onViewCreated() {
        // Override
    }

    // Lifecycle

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // BundleHelper.unpack()
    }

    override fun onResume() {
        super.onResume()

        mAlive = true
    }

    override fun onPause() {
        super.onPause()

        mSkeletonReceiver = null
        mAlive = false
        loading(false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // BundleHelper.pack()
    }

    fun alive(): Boolean {
        return mAlive
    }

    // ToolBar

    fun toolbar(): Toolbar? {
        return mToolbar
    }

    fun toolbar(b: Boolean) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return
        }
        val visibility = if (b) View.VISIBLE else View.GONE
        mToolbar!!.visibility = visibility
    }

    fun home(b: Boolean) {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return
        }
        actionBar.setDisplayHomeAsUpEnabled(b)
    }

    @Deprecated("")
    fun home(drawable: Drawable) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return
        }
        mToolbar!!.navigationIcon = drawable
    }

    @Deprecated("")
    fun icon(drawable: Drawable) {
        logo(drawable)
    }

    fun logo(drawable: Drawable?) {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return
        }
        actionBar.setDisplayShowHomeEnabled(drawable != null)
        actionBar.setDisplayUseLogoEnabled(drawable != null)
        actionBar.setLogo(drawable)
    }

    fun title(title: String?) {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return
        }
        actionBar.setDisplayShowTitleEnabled(!TextUtils.isEmpty(title))
        actionBar.title = title
    }

    fun title(): String? {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return null
        }
        val title = actionBar.title
        return title?.toString()
    }

    fun subtitle(subtitle: String) {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return
        }
        actionBar.setDisplayShowTitleEnabled(!TextUtils.isEmpty(subtitle))
        actionBar.subtitle = subtitle
    }

    fun subtitle(): String? {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return null
        }
        val subtitle = actionBar.subtitle
        return subtitle?.toString()
    }

    // MySwipeRefreshLayout

    fun mySwipeRefreshLayout(): MySwipeRefreshLayout? {
        return mMySwipeRefreshLayout
    }

    fun refreshable(): Boolean {
        return mMySwipeRefreshLayout != null && mMySwipeRefreshLayout!!.isEnabled
    }

    fun refreshable(b: Boolean, onRefreshListener: SwipeRefreshLayout.OnRefreshListener?) {
        // Resets loading count to avoid side-effects upon re-loading
        mLoading = 0
        if (!b) {
            mMySwipeRefreshLayout = null
        } else {
            if (mMySwipeRefreshLayout == null) {
                LogHelper.warning("MySwipeRefreshLayout was NULL")
                return
            }
            mMySwipeRefreshLayout!!.isRefreshing = false
            mMySwipeRefreshLayout!!.setOnRefreshListener(onRefreshListener)
        }
    }

    fun loading(): Int {
        return mLoading
    }

    fun loading(i: Int) {
        val count = mLoading + i
        if (count < 0) {
            loading(false)
            mLoading = 0
            return
        }
        if (mLoading == 0 && count > 0) {
            loading(true)
        } else if (mLoading > 0 && count == 0) {
            loading(false)
        }
        mLoading = count
    }

    fun loading(b: Boolean) {
        if (!b) {
            // Resets loading count to avoid side-effects upon re-loading
            mLoading = 0
        }
        if (mMySwipeRefreshLayout == null) {
            if (b) {
                LogHelper.warning("MySwipeRefreshLayout was NULL")
            }
            return
        }
        if (!b) {
            mMySwipeRefreshLayout!!.isRefreshing = false
            mMySwipeRefreshLayout!!.destroyDrawingCache()
            mMySwipeRefreshLayout!!.clearAnimation()
        } else if (!mMySwipeRefreshLayout!!.isRefreshing) {
            mMySwipeRefreshLayout!!.isRefreshing = true
        }
    }

    fun searchable(): Boolean {
        return mSkeletonReceiver != null
    }

    fun searchable(hint: String, skeletonReceiver: SkeletonReceiver?) {
        mSearchHint = hint
        mSkeletonReceiver = skeletonReceiver
        // AVOID supportInvalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (mSkeletonReceiver == null) {
            return super.onCreateOptionsMenu(menu)
        }

        menuInflater.inflate(R.menu.sk_search, menu)

        mSearchMenuItem = menu.findItem(R.id.menu_search)
        if (mSearchMenuItem == null) {
            LogHelper.warning("SearchMenuItem was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        mSearchView = mSearchMenuItem!!.actionView as SearchView
        if (mSearchView == null) {
            LogHelper.warning("SearchView was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        mSearchView!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        mSearchView!!.imeOptions = EditorInfo.IME_ACTION_SEARCH
        mSearchView!!.setIconifiedByDefault(true)
        mSearchView!!.queryHint = mSearchHint
        mSearchView!!.setOnCloseListener {
            mSkeletonReceiver!!.post(RESULT_SEARCH_CHANGE, "")
            false
        }
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(q: String): Boolean {
                stopSearch()
                mSkeletonReceiver!!.post(RESULT_SEARCH_SUBMIT, q)
                return true
            }

            override fun onQueryTextChange(q: String): Boolean {
                mSkeletonReceiver!!.post(RESULT_SEARCH_CHANGE, q)
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mSkeletonReceiver == null) {
            return super.onOptionsItemSelected(item)
        }
        val searchView = item.actionView as SearchView ?: return false
        if (item.itemId == R.id.menu_search) {
            searchView.isIconified = false
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun stopSearch() {
        mSearchMenuItem!!.collapseActionView()
        KeyboardHelper.hide(window)
        mSearchView!!.clearFocus()
    }

    // Navigation

    override fun onSupportNavigateUp(): Boolean {
        startActivity(IntentHelper.home())
        return true
    }

    override fun onBackPressed() {
        if (mSearchView != null && !mSearchView!!.isIconified) {
            stopSearch()
        } else {
            finish()
        }
    }

    // Runtime Permissions

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {

        // Search
        // <http://stackoverflow.com/q/18438890>

        val RESULT_SEARCH_CHANGE = "onQueryTextChange"
        val RESULT_SEARCH_SUBMIT = "onQueryTextSubmit"

    }

}
