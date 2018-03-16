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
import me.shkschneider.skeleton.extensions.isNull
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.java.ObjectHelper
import me.shkschneider.skeleton.network.NetworkHelper
import me.shkschneider.skeleton.network.WebService
import me.shkschneider.skeleton.network.WebServiceException
import me.shkschneider.skeleton.ui.*

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
        LogHelper.debug("IP: " + NetworkHelper.ipAddress("192.168.0.1"))
        toolbar?.title = "Skeleton"
        toolbar?.subtitle = "for Android"
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
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
        ScreenHelper.inches()
    }

    override fun onStop() {
        super.onStop()
        BroadcastHelper.unregister(mBroadcastReceiver)
    }

    private fun network() {
        WebService(WebService.Method.GET, URL)
                .callback(ShkMod::class.java, object : WebService.Callback<Any?> {
                    override fun success(result: Any?) {
                        if (result.isNull()) {
                            Toaster.lengthShort(ShkMod::class.java.simpleName)
                            return
                        }
                        // val shkMod = result as ShkMod
                        notification(DateTimeHelper.timestamp().toInt(), ShkMod::class.java.simpleName, ObjectHelper.jsonify(result!!))
                    }
                    override fun failure(e: WebServiceException) {
                        Toaster.lengthLong(WebServiceException::class.java.simpleName)
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
