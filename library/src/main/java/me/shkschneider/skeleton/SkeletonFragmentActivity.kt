package me.shkschneider.skeleton

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

abstract class SkeletonFragmentActivity : SkeletonActivity() {

    override fun getLifecycle(): Lifecycle {
        return super.getLifecycle()
    }

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
        if (supportFragmentManager?.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
            return
        }
        super.onBackPressed()
    }

}
