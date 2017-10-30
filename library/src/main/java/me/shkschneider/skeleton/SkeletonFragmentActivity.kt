package me.shkschneider.skeleton

import android.support.v4.app.Fragment
import android.os.Bundle

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
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager != null) {
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
                return
            }
        }
        super.onBackPressed()
    }

}
