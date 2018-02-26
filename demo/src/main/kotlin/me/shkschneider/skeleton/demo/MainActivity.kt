package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem

import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.demo.data.ShkMod
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.java.ClassHelper
import me.shkschneider.skeleton.java.ObjectHelper
import me.shkschneider.skeleton.network.*
import me.shkschneider.skeleton.ui.AnimationHelper
import me.shkschneider.skeleton.ui.BottomSheet
import me.shkschneider.skeleton.ui.Toaster

/**
 * SkeletonActivity
 * Collapsible Toolbar
 * ViewPager
 * FloatingActionButton
 * -> LocalBroadcast
 * -> Volley request (Proxy)
 * -> Notification (RunnableHelper.delay())
 * -> onNewIntent() (ActivityHelper.toast())
 */
class MainActivity : SkeletonActivity() {

    private val mBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // val code = intent.getIntExtra(BROADCAST_SECRET_CODE, 0)
            network()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar?.title = "Skeleton"
        toolbar?.subtitle = "for Android"
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                when (position) {
                    0 -> return ShkFragment()
                    1 -> return SkFragment()
                }
                return null
            }
            override fun getPageTitle(position: Int): CharSequence? {
                when (position) {
                    0 -> return "ShkSchneider"
                    1 -> return "Skeleton"
                }
                return null
            }
            override fun getCount(): Int {
                return 2
            }
        }
        viewPager.offscreenPageLimit = viewPager.adapter.count
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        for (i in 0 until viewPager.adapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.adapter.getPageTitle(i)))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Ignore
            }
            override fun onPageSelected(position: Int) {
                LogHelper.verbose("Page: " + position)
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
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        BroadcastHelper.register(mBroadcastReceiver, IntentFilter(BROADCAST_SECRET))
    }

    override fun onResume() {
        super.onResume()
        DeviceHelper.screenSize()
    }

    override fun onStop() {
        super.onStop()
        BroadcastHelper.unregister(mBroadcastReceiver)
    }

    private fun network() {
        WebService(WebService.Method.GET, URL)
                .callback(ShkMod.javaClass, object : WebService.Callback<Any?> {
                    override fun success(result: Any?) {
                        if (result == null) {
                            Toaster.lengthShort(ClassHelper.simpleName(ShkMod::class.java))
                            return
                        }
                        // val shkMod = result as ShkMod
                        notification(DateTimeHelper.timestamp().toInt(), ClassHelper.simpleName(ShkMod::class.java), ObjectHelper.jsonify(result))
                    }
                    override fun failure(e: WebServiceException) {
                        Toaster.lengthLong(ClassHelper.simpleName(e::class.java))
                    }
                }).run()
    }

    private fun notification(id: Int, title: String, message: String) {
        val intent = Intent(baseContext, MainActivity::class.java)
                .putExtra("title", title)
                .putExtra("message", message)
        val channel = NotificationHelper.Channel(id.toString(), id.toString(), true, true, true)
        // final NotificationChannel notificationChannel = channel.get();
        val notificationBuilder = NotificationHelper.Builder(channel)
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

    companion object {

        val BROADCAST_SECRET = "BROADCAST_SECRET"
        val BROADCAST_SECRET_CODE = "BROADCAST_SECRET_CODE"
        val URL = "https://raw.githubusercontent.com/shkschneider/android_manifest/master/VERSION.json"

    }

}
