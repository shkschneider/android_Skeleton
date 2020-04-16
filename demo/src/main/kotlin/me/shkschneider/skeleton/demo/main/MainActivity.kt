package me.shkschneider.skeleton.demo.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.demo.R
import me.shkschneider.skeleton.demo.about.AboutActivity
import me.shkschneider.skeleton.extensions.Intent
import me.shkschneider.skeleton.helperx.log.Logger
import me.shkschneider.skeleton.viewModel
import org.koin.android.ext.android.inject
import java.util.UUID

class MainActivity : SkeletonActivity() {

    val uuid: UUID by inject()

    val secretKey by lazy { secretKey() }

    init {
        System.loadLibrary("secrets")
    }

    external fun secretKey(): String

    // region lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // overridePendingTransitions(0, 0)
        setContentView(R.layout.activity_main)
        toolbar(home = false, title = getString(R.string.title), subtitle = getString(R.string.subtitle))

        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        for (i in 0 until pagerAdapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(pagerAdapter.getPageTitle(i)))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.run {
            adapter = pagerAdapter
            offscreenPageLimit = pagerAdapter.count / 2
            addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
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

    // enregion

    // region viewmodel

    private val viewModel by viewModel<MainViewModel>()

    override fun onResume() {
        super.onResume()

        viewModel.models().observe(this, Observer {
            // update UI
            @Suppress("UNUSED_VARIABLE") val breakpoint: Nothing? = null
        })
    }

    // endregion

    // region fragments

    private class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        private val shkFragment by lazy {
            SkeletonFragment.newInstance(ShkFragment::class)
        }
        private val skFragment by lazy {
            SkeletonFragment.newInstance(SkFragment::class)
        }

        @Throws(IndexOutOfBoundsException::class)
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> shkFragment
                1 -> skFragment
                else -> throw IndexOutOfBoundsException()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return getItem(position).javaClass.simpleName.removeSuffix("Fragment")
        }

        override fun getCount(): Int {
            return 2
        }

    }

    // endregion

}
