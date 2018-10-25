package me.shkschneider.skeleton

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @see SkeletonActivity
 */
abstract class SkeletonFragmentActivity : SkeletonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sk_fragmentactivity)
    }

    protected fun setContentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
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
