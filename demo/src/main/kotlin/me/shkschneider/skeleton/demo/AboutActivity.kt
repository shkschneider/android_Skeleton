package me.shkschneider.skeleton.demo

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View

import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.extensions.camelCase
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper

class AboutActivity : SkeletonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        home(true)
        toolbar?.let {
            it.title = "Skeleton"
            it.subtitle = "for Android"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class AboutFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(bundle: Bundle, rootKey: String) {
            addPreferencesFromResource(R.xml.about)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            // Application
            with(findPreference("app_package")) {
                title = "PackageName"
                summary = ApplicationHelper.packageName()
            }
            with(findPreference("app_versionName")) {
                title = "VersionName"
                summary = ApplicationHelper.versionName()
            }
            with(findPreference("app_versionCode")) {
                title = "VersionCode"
                summary = ApplicationHelper.versionCode().toString()
            }
            with(findPreference("app_variant")) {
                title = "Variant"
                summary = (if (ApplicationHelper.debuggable()) "Debug" else "Release")
            }
            with(findPreference("app_flavor")) {
                title = "Flavor"
                summary = BuildConfig.FLAVOR.camelCase()
            }
            // OS
            with(findPreference("os_version")) {
                title = "Version"
                summary = AndroidHelper.codename()
            }
            with(findPreference("os_api")) {
                title = "API"
                summary = AndroidHelper.api().toString()
            }
        }

    }

}
