package me.shkschneider.skeleton.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.SkeletonFragment
import me.shkschneider.skeleton.datax.MyDatabase
import me.shkschneider.skeleton.datax.MyModel
import me.shkschneider.skeleton.extensions.android.Intent
import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.kotlinx.Coroutines
import me.shkschneider.skeleton.uix.BottomSheet

class MainActivity : SkeletonActivity() {

    // region lifecycle

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleHelper.Application.switch(newBase, LocaleHelper.Device.locale()))
//    }

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

    // enregion

    // region coroutines

    lateinit var job: Job

    override fun onResume() {
        super.onResume()

        // options 1: using blocks
        job = Coroutines.launch(Dispatchers.IO, {
            val db = MyDatabase.get()
            db.clearAllTables()
            db.myModelDao().insert(MyModel(userName = "user.name1"))
            db.myModelDao().insert(MyModel(userName = "user.name2"))
            return@launch MyDatabase.get().myModelDao().getAll()
        }) {
            updateModels(it)
        }

        // option 2: using methods
        // job = Coroutines.launch(Dispatchers.IO, ::getModels, ::updateModels)
    }

    private suspend fun getModels() = Coroutines.async(Dispatchers.IO) {
        val db = MyDatabase.get()
        db.clearAllTables()
        db.myModelDao().insert(MyModel(userName = "user.name1"))
        db.myModelDao().insert(MyModel(userName = "user.name2"))
        return@async MyDatabase.get().myModelDao().getAll()
    }

    private fun updateModels(models: List<MyModel>?) {
        val breakpoint: Nothing? = null
    }

    override fun onPause() {
        super.onPause()

        job.cancel()
    }

    // endregion

    private fun bottomSheet(title: String, content: String) {
        BottomSheet.Builder(this)
                .setTitle(title)
                .setContent(content)
                .setPositive(resources.getString(android.R.string.ok), null)
                .setNegative(resources.getString(android.R.string.cancel), null)
                .build()
                .show()
    }

    // region fragments

    private class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        private val shkFragment by lazy {
            SkeletonFragment.newInstance(ShkFragment::class)
        }
        private val skFragment by lazy {
            SkeletonFragment.newInstance(SkFragment::class)
        }

        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 -> shkFragment
                1 -> skFragment
                else -> null
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return getItem(position)?.javaClass?.simpleName
        }

        override fun getCount(): Int {
            return 2
        }

    }

    // endregion

}
