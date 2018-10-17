package me.shkschneider.skeleton

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import me.shkschneider.skeleton.extensions.toStringOrEmpty
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.ui.OverlayLoader

/**
 * https://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 * https://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html
 *
 * +-------+---------+------------+
 * | Alive | Visible | Foreground |
 * +-------+---------+------------+
 * |     x |         |            | onCreate()
 * |     x |       x |            | onStart()
 * |     x |       x |            | onPostCreate()
 * |     x |       x |          x | onResume()
 * |     x |       x |          x | onPostResume()
 * |     x |       x |          x | onResumeFragments()
 * |     x |       x |          x | onAttachedToWindow()
 * |     x |       x |          x | onPause()
 * |     x |       x |            | onStop()
 * |     x |         |            | onDestroy()
 * +-------+---------+------------+
 */
abstract class SkeletonActivity : AppCompatActivity() {

    protected var toolbar: Toolbar? = null

    private var alive = false
    private var enterAnim: Int? = null
    private var exitAnim: Int? = null

    // Refresh
    // <https://gist.github.com/antoniolg/9837398>

    private var loading = 0

    // Search
    // <http://stackoverflow.com/q/18438890>

    private var searchHint: String? = null
    private var skeletonReceiver: SkeletonReceiver? = null
    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
    }

    override fun attachBaseContext(newBase: Context?) {
        // LocaleHelper.Application.switch()
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (enterAnim != null && exitAnim != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                // <https://stackoverflow.com/a/28989946/603270>
                window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            }
        }
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.sk_activity)
        statusBarColor(window, ContextCompat.getColor(applicationContext, R.color.statusBarColor))
    }

    fun overridePendingTransitions(@AnimRes enterAnim: Int, @AnimRes exitAnim: Int) {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Logger.verbose("onNewIntent ${intent.action.orEmpty().toUpperCase()}"
                + if (intent.extras != null) " (has extras)" else "")
        // This part is necessary to ensure that getIntent returns the latest intent when
        // this activity is started. By default, getIntent() returns the initial intent
        // that was set from another activity that started this activity.
        setIntent(intent)
    }

    // <https://stackoverflow.com/a/27635961/603270>
    override fun onResumeFragments() {
        super.onResumeFragments()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        onViewCreated()
    }

    override fun setContentView(@LayoutRes id: Int) {
        super.setContentView(id)
        onViewCreated()
    }

    override fun setContentView(view: View, layoutParams: ViewGroup.LayoutParams) {
        super.setContentView(view, layoutParams)
        onViewCreated()
    }

    fun statusBarColor(window: Window): Int {
        if (Build.VERSION.SDK_INT >= 21) {
            return window.statusBarColor
        } else {
            return Color.TRANSPARENT // Color.BLACK
        }
    }

    fun statusBarColor(window: Window, @ColorInt color: Int): Boolean {
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
            return true
        }
        return false
    }

    fun toolbarColor(@ColorInt color: Int): Boolean? {
        toolbar?.setBackgroundColor(color) ?: run {
            Logger.warning("Toolbar was NULL")
            return false
        }
        return true
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        try {
            super.setSupportActionBar(toolbar)
        } catch (e: IllegalStateException) {
            /**
             * java.lang.RuntimeException: Unable to start activity
             * java.lang.IllegalStateException: This Activity already has an action bar supplied by the window decor.
             * Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.
             */
            e.printStackTrace()
        }
        this.toolbar = toolbar
    }

    // Lifecycle

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // BundleHelper.unpack()
    }

    private fun onViewCreated() {
        toolbar = findViewById(R.id.toolbar)
        toolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
            title(ApplicationHelper.name())
        }
    }

    override fun onResume() {
        super.onResume()
        alive = true
        if (enterAnim != null && exitAnim != null) {
            overridePendingTransition(enterAnim!!, exitAnim!!)
        }
    }

    override fun onPause() {
        super.onPause()
        skeletonReceiver = null
        alive = false
        if (enterAnim != null && exitAnim != null) {
            overridePendingTransition(enterAnim!!, exitAnim!!)
        }
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

    @Deprecated("Never existed.", ReplaceWith("recreate()"))
    fun restart() {
        recreate()
    }

    // ToolBar

    fun toolbar(): Toolbar? {
        return toolbar
    }

    fun toolbar(show: Boolean = true, home: Boolean = false, logo: Drawable? = null, title: String? = null, subtitle: String? = null) {
        toolbar(show)
        home(home)
        logo(logo)
        title(title)
        subtitle(subtitle)
    }

    fun toolbar(b: Boolean) {
        toolbar?.visibility = if (b) View.VISIBLE else View.GONE
    }

    fun home(b: Boolean) {
        supportActionBar?.setDisplayShowHomeEnabled(b)
        supportActionBar?.setDisplayHomeAsUpEnabled(b)
    }

    @Deprecated("NavigationIcon is probably NOT what you want.", ReplaceWith("logo(drawable)"))
    fun icon(drawable: Drawable?) {
        logo(drawable)
    }

    fun logo(drawable: Drawable?) {
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayShowHomeEnabled(drawable != null)
            actionBar.setDisplayUseLogoEnabled(drawable != null)
            actionBar.setLogo(drawable)
        }
    }

    fun title(title: String?) {
        toolbar?.let { toolbar ->
            toolbar.title = title
            return
        }
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayShowTitleEnabled(! title.isNullOrEmpty())
            actionBar.title = title
        }
    }

    fun title(): String? {
        toolbar?.let { toolbar ->
            return toolbar.title.toStringOrEmpty()
        }
        supportActionBar?.let { actionBar ->
            return actionBar.title.toStringOrEmpty()
        }
        return null
    }

    fun subtitle(subtitle: String?) {
        toolbar?.let { toolbar ->
            toolbar.subtitle = subtitle
        }
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayShowTitleEnabled(! (title.isNullOrEmpty() && subtitle.isNullOrEmpty()))
            actionBar.subtitle = subtitle
        }
    }

    fun subtitle(): String? {
        toolbar?.let { toolbar ->
            return toolbar.subtitle.toStringOrEmpty()
        }
        supportActionBar?.let { actionBar ->
            return actionBar.subtitle.toStringOrEmpty()
        }
        return null
    }

    // Loading

    protected var overlayLoader: OverlayLoader? = null

    fun loading(): Int {
        return loading
    }

    fun loading(i: Int) {
        loading += i
        loading(loading > 0)
    }

    fun loading(b: Boolean) {
        if (!ThreadHelper.mainThread()) {
            Logger.debug("Not on Main UI Thread!")
        }
        if (b) {
            runOnUiThread {
                overlayLoader ?: run {
                    overlayLoader = OverlayLoader.show(this)
                }
            }
        } else {
            runOnUiThread {
                overlayLoader?.hide(this)?.also { loading = 0 }
                overlayLoader = null
            }
        }
    }

    // Search

    fun searchable(): Boolean {
        return skeletonReceiver != null
    }

    fun searchable(hint: String, skeletonReceiver: SkeletonReceiver?) {
        searchHint = hint
        this.skeletonReceiver = skeletonReceiver
        // AVOID supportInvalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val skeletonReceiver = this.skeletonReceiver ?: run {
            return super.onCreateOptionsMenu(menu)
        }
        menuInflater.inflate(R.menu.sk_search, menu)
        searchMenuItem = menu.findItem(R.id.menu_search)
        val searchMenuItem = this.searchMenuItem ?: run {
            Logger.warning("SearchMenuItem was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        searchView = searchMenuItem.actionView as SearchView
        val searchView = this.searchView ?: run {
            Logger.warning("SearchView was NULL")
            return super.onCreateOptionsMenu(menu)
        }
        searchView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.setIconifiedByDefault(true)
        searchView.queryHint = searchHint
        searchView.setOnCloseListener {
            skeletonReceiver.post(RESULT_SEARCH_CHANGE, "")
            return@setOnCloseListener false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                stopSearch()
                skeletonReceiver.post(RESULT_SEARCH_SUBMIT, q)
                return true
            }

            override fun onQueryTextChange(q: String): Boolean {
                skeletonReceiver.post(RESULT_SEARCH_CHANGE, q)
                return true
            }
        })
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
        val searchMenuItem = this.searchMenuItem ?: return
        searchMenuItem.collapseActionView()
        KeyboardHelper.hide(window)
        val searchView = this.searchView ?: return
        searchView.clearFocus()
    }

    // Navigation

    override fun onSupportNavigateUp(): Boolean {
        startActivity(IntentHelper.main())
        return true
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    override fun onBackPressed() {
        if (searchView?.isIconified == true) {
            stopSearch()
            return
        }
        finish()
    }

    // Runtime Permissions

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {

        const val RESULT_SEARCH_CHANGE = "onQueryTextChange"
        const val RESULT_SEARCH_SUBMIT = "onQueryTextSubmit"

    }

}
