package me.shkschneider.skeleton.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.extensions.then
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.Metrics

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

    class AboutFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.about, rootKey)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            // If preferences have no icons, this is needed to get rid of left's empty space
            // In Material (preference_material.xml) PreferenceCompat (android.R.id.icon): minWidth=60dp
            listView?.setPadding(-1 * Metrics.pixelsFromDp(60.toFloat()), 0, 0, 0)
            return view
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
                summary = (ApplicationHelper.debuggable() then "debug" ?: "release").toUpperCase()
            }
            with(findPreference("app_flavor")) {
                title = "Flavor"
                summary = BuildConfig.FLAVOR.toUpperCase()
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
