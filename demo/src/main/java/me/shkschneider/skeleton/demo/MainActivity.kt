package me.shkschneider.skeleton.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.extensions.Intent
import me.shkschneider.skeleton.helper.Logger
import me.shkschneider.skeleton.ui.BottomSheet

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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        when (intent.action) {
            ShkMod.BROADCAST_SECRET -> {
                val title = intent.getStringExtra("title")
                val message = intent.getStringExtra("message")
                bottomSheet(title, message)
            }
        }
    }

    private fun bottomSheet(title: String, content: String) {
        BottomSheet.Builder(this)
                .setTitle(title)
                .setContent(content)
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

}
