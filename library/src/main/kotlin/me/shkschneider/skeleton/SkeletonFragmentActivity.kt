package me.shkschneider.skeleton

import android.os.Bundle
import android.support.v4.app.Fragment
import me.shkschneider.skeleton.extensions.isNotNull

abstract class SkeletonFragmentActivity : SkeletonActivity() {

    protected fun setContentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sk_fragmentactivity)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        // fragmentManager.executePendingTransactions()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.isNotNull()) {
            if (fragmentManager.backStackEntryCount != 0) {
                fragmentManager.popBackStack()
                return
            }
        }
        super.onBackPressed()
    }

}
