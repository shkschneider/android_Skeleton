package me.shkschneider.skeleton.demo.about

import android.os.Bundle
import me.shkschneider.skeleton.android.app.SkeletonActivity
import me.shkschneider.skeleton.demo.R

class AboutActivity : SkeletonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar(home = true, title = getString(R.string.title))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
