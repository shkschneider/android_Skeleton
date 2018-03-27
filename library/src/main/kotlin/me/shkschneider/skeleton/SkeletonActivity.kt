package me.shkschneider.skeleton

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import me.shkschneider.skeleton.extensions.isNull
import me.shkschneider.skeleton.helper.*
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

    protected var toolbar: Toolbar? = null

    private var alive = false

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private var loading = 0

    private var searchHint: String? = null
    private var skeletonReceiver: SkeletonReceiver? = null
    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null

    override fun attachBaseContext(newBase: Context?) {
        // LocaleHelper.Application.switch()
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.sk_activity)
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            statusBarColor(window, ContextCompat.getColor(applicationContext, R.color.statusBarColor))
            val name = ApplicationHelper.name()
            val icon = ApplicationHelper.icon()
            val color = ContextCompat.getColor(applicationContext, R.color.primaryColor)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setTaskDescription(ActivityManager.TaskDescription(name, icon, color))
            }
        }
        intent?.extras?.let {
            Logger.verbose("onNewIntent: $intent")
            onNewIntent(intent) // FIXME keep?
        }
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

    fun statusBarColor(window: Window): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return window.statusBarColor
        } else {
            return Color.TRANSPARENT // Color.BLACK
        }
    }

    fun statusBarColor(window: Window, @ColorInt color: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            return true
        }
        return false
    }

    fun toolbarColor(@ColorInt color: Int): Boolean? {
        if (toolbar.isNull()) {
            Logger.warning("Toolbar was NULL")
            return false
        }
        toolbar?.setBackgroundColor(color)
        return true
    }

    protected fun bindToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.let {
            // Logger.verbose("Found a Toolbar");
            setSupportActionBar(it)
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
        alive = true
    }

    override fun onPause() {
        super.onPause()
        skeletonReceiver = null
        alive = false
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
        return alive
    }

    // ToolBar

    fun toolbar(): Toolbar? {
        return toolbar
    }

    fun toolbar(b: Boolean) {
        toolbar ?: Logger.warning("Toolbar was NULL")
        toolbar?.let {
            it.visibility = if (b) View.VISIBLE else View.GONE
        }
    }

    fun home(b: Boolean) {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(b)
        }
    }

    @Deprecated("Avoid")
    fun home(drawable: Drawable) {
        toolbar ?: Logger.warning("Toolbar was NULL")
        toolbar?.let {
            it.navigationIcon = drawable
        }
    }

    @Deprecated("Use logo()")
    fun icon(drawable: Drawable) {
        logo(drawable)
    }

    fun logo(drawable: Drawable?) {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(drawable != null)
            it.setDisplayUseLogoEnabled(drawable != null)
            it.setLogo(drawable)
        }
    }

    fun title(title: String?) {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(! title.isNullOrEmpty())
            it.title = title
        }
    }

    fun title(): String? {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        return supportActionBar?.subtitle?.toString()
    }

    fun subtitle(subtitle: String?) {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(! title.isNullOrEmpty() || subtitle.isNullOrEmpty())
            it.subtitle = subtitle
        }
    }

    fun subtitle(): String? {
        supportActionBar ?: Logger.warning("ActionBar was NULL")
        return supportActionBar?.subtitle?.toString()
    }

    // Loading

    protected var overlayLoader: OverlayLoader? = null

    fun loading(): Int {
        return loading
    }

    fun loading(i: Int) {
        val count = loading + i
        if (count < 0) {
            loading(false)
            loading = 0
            return
        }
        if (loading == 0 && count > 0) {
            loading(true)
        } else if (loading > 0 && count == 0) {
            loading(false)
        }
        loading = count
    }

    fun loading(b: Boolean) {
        if (! b) {
            // Resets loading count to avoid side-effects upon re-loading
            loading = 0
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
        return skeletonReceiver != null
    }

    fun searchable(hint: String, skeletonReceiver: SkeletonReceiver?) {
        searchHint = hint
        this.skeletonReceiver = skeletonReceiver
        // AVOID supportInvalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (skeletonReceiver == null) {
            return super.onCreateOptionsMenu(menu)
        }
        with (skeletonReceiver!!) {
            menuInflater.inflate(R.menu.sk_search, menu)
            searchMenuItem = menu.findItem(R.id.menu_search)
            if (searchMenuItem == null) {
                Logger.warning("SearchMenuItem was NULL")
                return super.onCreateOptionsMenu(menu)
            }
            searchView = searchMenuItem!!.actionView as SearchView
            if (searchView == null) {
                Logger.warning("SearchView was NULL")
                return super.onCreateOptionsMenu(menu)
            }
            with (searchView!!) {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                imeOptions = EditorInfo.IME_ACTION_SEARCH
                setIconifiedByDefault(true)
                queryHint = searchHint
                setOnCloseListener {
                    post(RESULT_SEARCH_CHANGE, "")
                    false
                }
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(q: String): Boolean {
                        stopSearch()
                        post(RESULT_SEARCH_SUBMIT, q)
                        return true
                    }

                    override fun onQueryTextChange(q: String): Boolean {
                        post(RESULT_SEARCH_CHANGE, q)
                        return true
                    }
                })
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        skeletonReceiver ?: return super.onOptionsItemSelected(item)
        val searchView = item.actionView as SearchView
        if (item.itemId == R.id.menu_search) {
            searchView.isIconified = false
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun stopSearch() {
        searchMenuItem ?: return
        searchMenuItem!!.collapseActionView()
        KeyboardHelper.hide(window)
        searchView ?: return
        searchView!!.clearFocus()
    }

    // Navigation

    override fun onSupportNavigateUp(): Boolean {
        startActivity(IntentHelper.main())
        return true
    }

    override fun onBackPressed() {
        if (searchView != null && !searchView!!.isIconified) {
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

        val RESULT_SEARCH_CHANGE = "onQueryTextChange"
        val RESULT_SEARCH_SUBMIT = "onQueryTextSubmit"

    }

}
