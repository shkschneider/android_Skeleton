package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.demo.data.ShkMod
import me.shkschneider.skeleton.extensions.Intent
import me.shkschneider.skeleton.extensions.simpleName
import me.shkschneider.skeleton.extensions.toStringOrEmpty
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.network.WebService
import me.shkschneider.skeleton.ui.AnimationHelper
import me.shkschneider.skeleton.ui.BottomSheet
import me.shkschneider.skeleton.ui.Toaster

/**
 * SkeletonActivity
 * Collapsible Toolbar
 * ViewPager
 * FloatingActionButton
 * -> LocalBroadcast
 * -> WebService request
 * -> Notification (RunnableHelper.delay())
 * -> onNewIntent() (Toaster.show())
 */
class MainActivity : SkeletonActivity() {

    private val mBroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // val code = intent.getIntExtra(BROADCAST_SECRET_CODE, 0)
            network()
        }
    }

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleHelper.Application.switch(newBase, LocaleHelper.Device.locale()))
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // overridePendingTransitions(0, 0)
        setContentView(R.layout.activity_main)
        toolbar(home = false, title = getString(R.string.title), subtitle = getString(R.string.subtitle))

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        for (i in 0 until pagerAdapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(pagerAdapter.getPageTitle(i)))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Ignore
            }
            override fun onPageSelected(position: Int) {
                Logger.verbose("Page: $position")
            }
            override fun onPageScrollStateChanged(state: Int) {
                // Ignore
            }
        })

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setImageResource(android.R.drawable.ic_dialog_info)
        floatingActionButton.setOnClickListener {
            val intent = Intent(BROADCAST_SECRET).putExtra(BROADCAST_SECRET_CODE, 42)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
        AnimationHelper.revealOn(floatingActionButton)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                startActivity(Intent(this, AboutActivity::class))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        BroadcastHelper.register(mBroadcastReceiver, IntentFilter(BROADCAST_SECRET))
    }

    override fun onStop() {
        super.onStop()
        BroadcastHelper.unregister(mBroadcastReceiver)
    }

    private fun network() {
        WebService(WebService.Method.GET, URL)
                .callback(object: WebService.Callback {
                    override fun success(result: WebService.Response?) {
                        result?.let {
                            notification(DateTimeHelper.timestamp(), ShkMod::class.simpleName(),
                                    it.message.toStringOrEmpty())
                        } ?: run {
                            Toaster.show(result.toStringOrEmpty())
                        }
                    }
                    override fun failure(e: WebService.Error) {
                        Toaster.show(e.toStringOrEmpty())
                    }
                })
                .run()
    }

    private fun notification(id: Int, title: String, message: String) {
        val intent = Intent(applicationContext, MainActivity::class)
                .putExtra("title", title)
                .putExtra("message", message)
        val channel = NotificationHelper.Channel(id.toString(), id.toString(), true, true, true)
        // final NotificationChannel notificationChannel = channel.get();
        val notificationBuilder = NotificationHelper.Builder(channel)
                .setShowWhen(false)
                .setContentTitle("Skeleton")
                .setContentText("for Android")
                .setContentIntent(NotificationHelper.pendingIntent(this, intent))
                .setTicker("Sk!")
                .setColor(ContextCompat.getColor(applicationContext, R.color.accentColor))
                .setSmallIcon(ApplicationHelper.DEFAULT_ICON)
                .setNumber(42)
        NotificationHelper.notify(0, notificationBuilder.build())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")
        BottomSheet.Builder(this)
                .setTitle(title)
                .setContent(message)
                .setPositive(resources.getString(android.R.string.ok), null)
                .setNegative(resources.getString(android.R.string.cancel), null)
                .build()
                .show()
    }

    private class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> ShkFragment()
                1 -> SkFragment()
                else -> null
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "ShkSchneider"
                1 -> "Skeleton"
                else -> null
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }

    companion object {

        const val BROADCAST_SECRET = "BROADCAST_SECRET"
        const val BROADCAST_SECRET_CODE = "BROADCAST_SECRET_CODE"
        const val URL = "https://raw.githubusercontent.com/shkschneider/android_manifest/master/VERSION.json"

    }

}
