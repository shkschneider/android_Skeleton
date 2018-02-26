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
import me.shkschneider.skeleton.ui.OverlayLoader

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
 *
 * Toolbar
 * OverlayLoader
 */
abstract class SkeletonActivity : AppCompatActivity() {

    // Search
    // <http://stackoverflow.com/q/18438890>

    companion object {

        const val RESULT_SEARCH_CHANGE = "onQueryTextChange"
        const val RESULT_SEARCH_SUBMIT = "onQueryTextSubmit"

    }

    protected var toolbar: Toolbar? = null

    private var _alive = false

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private var _loading: Int = 0

    private var _searchHint: String? = null
    private var _skeletonReceiver: SkeletonReceiver? = null
    private var _searchMenuItem: MenuItem? = null
    private var _searchView: SearchView? = null

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
        onViewCreated()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        onViewCreated()
    }

    override fun setContentView(view: View, layoutParams: ViewGroup.LayoutParams) {
        super.setContentView(view, layoutParams)
        onViewCreated()
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
        if (toolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return false
        }
        toolbar!!.setBackgroundColor(color)
        return true
    }

    protected fun bindToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            // LogHelper.verbose("Found a Toolbar");
            setSupportActionBar(toolbar)
            title(ApplicationHelper.name())
        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        this.toolbar = toolbar
    }

    protected fun onViewCreated() {
        bindToolbar()
    }

    // Lifecycle

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // BundleHelper.unpack()
    }

    override fun onResume() {
        super.onResume()
        _alive = true
    }

    override fun onPause() {
        super.onPause()
        _skeletonReceiver = null
        _alive = false
    }

    override fun onStop() {
        super.onStop()
        loading(false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // BundleHelper.pack()
    }

    fun alive(): Boolean {
        return _alive
    }

    // ToolBar

    fun toolbar(): Toolbar? {
        return toolbar
    }

    fun toolbar(b: Boolean) {
        if (toolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return
        }
        val visibility = if (b) View.VISIBLE else View.GONE
        toolbar!!.visibility = visibility
    }

    fun home(b: Boolean) {
        val actionBar = supportActionBar
        if (actionBar == null) {
            LogHelper.warning("ActionBar was NULL")
            return
        }
        actionBar.setDisplayHomeAsUpEnabled(b)
    }

    @Deprecated("Avoid")
    fun home(drawable: Drawable) {
        if (toolbar == null) {
            LogHelper.warning("Toolbar was NULL")
            return
        }
        toolbar!!.navigationIcon = drawable
    }

    @Deprecated("Use logo()")
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

    // Loading

    protected var overlayLoader: OverlayLoader? = null

    fun loading(): Int {
        return _loading
    }

    fun loading(i: Int) {
        val count = _loading + i
        if (count < 0) {
            loading(false)
            _loading = 0
            return
        }
        if (_loading == 0 && count > 0) {
            loading(true)
        } else if (_loading > 0 && count == 0) {
            loading(false)
        }
        _loading = count
    }

    fun loading(b: Boolean) {
        if (! b) {
            // Resets loading count to avoid side-effects upon re-loading
            _loading = 0
        }
        if (b) {
            if (overlayLoader != null) {
                overlayLoader = OverlayLoader.show(this)
            }
        } else {
            overlayLoader?.hide(this)
            overlayLoader = null
        }
    }

    fun searchable(): Boolean {
        return _skeletonReceiver != null
    }

    fun searchable(hint: String, skeletonReceiver: SkeletonReceiver?) {
        _searchHint = hint
        _skeletonReceiver = skeletonReceiver
        // AVOID supportInvalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (_skeletonReceiver == null) {
            return super.onCreateOptionsMenu(menu)
        }
        menuInflater.inflate(R.menu.sk_search, menu)
        _searchMenuItem = menu.findItem(R.id.menu_search)
        if (_searchMenuItem == null) {
            LogHelper.warning("SearchMenuItem was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        _searchView = _searchMenuItem!!.actionView as SearchView
        if (_searchView == null) {
            LogHelper.warning("SearchView was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        _searchView!!.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        _searchView!!.imeOptions = EditorInfo.IME_ACTION_SEARCH
        _searchView!!.setIconifiedByDefault(true)
        _searchView!!.queryHint = _searchHint
        _searchView!!.setOnCloseListener {
            _skeletonReceiver!!.post(RESULT_SEARCH_CHANGE, "")
            false
        }
        _searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                stopSearch()
                _skeletonReceiver!!.post(RESULT_SEARCH_SUBMIT, q)
                return true
            }
            override fun onQueryTextChange(q: String): Boolean {
                _skeletonReceiver!!.post(RESULT_SEARCH_CHANGE, q)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (_skeletonReceiver == null) {
            return super.onOptionsItemSelected(item)
        }
        val searchView = item.actionView as SearchView
        if (item.itemId == R.id.menu_search) {
            searchView.isIconified = false
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun stopSearch() {
        _searchMenuItem!!.collapseActionView()
        KeyboardHelper.hide(window)
        _searchView!!.clearFocus()
    }

    // Navigation

    override fun onSupportNavigateUp(): Boolean {
        startActivity(IntentHelper.main())
        return true
    }

    override fun onBackPressed() {
        if (_searchView != null && !_searchView!!.isIconified) {
            stopSearch()
        } else {
            finish()
        }
    }

    // Runtime Permissions

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
