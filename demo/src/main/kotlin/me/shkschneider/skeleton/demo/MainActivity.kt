package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
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
import me.shkschneider.skeleton.extensions.klass
import me.shkschneider.skeleton.helper.*
import me.shkschneider.skeleton.network.NetworkHelper
import me.shkschneider.skeleton.network.WebService
import me.shkschneider.skeleton.security.FingerprintHelper
import me.shkschneider.skeleton.ui.*

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

    private val mBroadcastReceiver = object : BroadcastReceiver() {
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
        setContentView(R.layout.activity_main)
        Logger.debug("IP: " + NetworkHelper.ipAddress("192.168.0.1"))
        toolbar?.title = getString(R.string.title)
        toolbar?.subtitle = getString(R.string.subtitle)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        for (i in 0 until pagerAdapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(pagerAdapter.getPageTitle(i)))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

    private var cancellationSignal: CancellationSignal? = null

    override fun onResume() {
        super.onResume()
        ScreenHelper.inches()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && FingerprintHelper.available()) {
            cancellationSignal = FingerprintHelper.background(object: FingerprintManager.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    Toaster.show("Fingerprint recognized!")
                }
                override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                    super.onAuthenticationHelp(helpCode, helpString)
                    Toaster.show(helpString?.toString().orEmpty())
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    Toaster.show(errString?.toString().orEmpty())
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        BroadcastHelper.unregister(mBroadcastReceiver)
        FingerprintHelper.cancel(cancellationSignal)
    }

    private fun network() {
        WebService(WebService.Method.GET, URL)
                .callback(object : WebService.Callback {
                    override fun success(result: WebService.Response?) {
                        if (result?.message?.isNotBlank() == true) {
                            notification(DateTimeHelper.timestamp(), ShkMod::class.java.simpleName, result.message!!)
                        }
                    }
                    override fun failure(e: WebService.Error) {
                        Toaster.show(WebService.Error::class.java.simpleName)
                    }
                }).run()
    }

    private fun notification(id: Int, title: String, message: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
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

        val BROADCAST_SECRET = "BROADCAST_SECRET"
        val BROADCAST_SECRET_CODE = "BROADCAST_SECRET_CODE"
        val URL = "https://raw.githubusercontent.com/shkschneider/android_manifest/master/VERSION.json"

    }

}
