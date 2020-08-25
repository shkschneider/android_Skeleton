package me.shkschneider.skeleton.demo.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import me.shkschneider.skeleton.android.BuildConfig
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.os.AndroidHelper
import me.shkschneider.skeleton.android.util.Metrics
import me.shkschneider.skeleton.demo.R
import java.util.Locale

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
        findPreference<Preference>("app_package")?.run {
            title = "PackageName"
            summary = ApplicationHelper.packageName
        }
        findPreference<Preference>("app_versionName")?.run {
            title = "VersionName"
            summary = ApplicationHelper.versionName()
        }
        findPreference<Preference>("app_versionCode")?.run {
            title = "VersionCode"
            summary = ApplicationHelper.versionCode().toString()
        }
        findPreference<Preference>("app_revision")?.run {
            title = "Revision"
            summary =  BuildConfig.REVISION
        }
        findPreference<Preference>("app_variant")?.run {
            title = "Variant"
            summary = (if (ApplicationHelper.debuggable) "debug" else "release").toUpperCase(Locale.getDefault())
        }
        // OS
        findPreference<Preference>("os_version")?.run {
            title = "Version"
            summary = AndroidHelper.codename()
        }
        findPreference<Preference>("os_api")?.run {
            title = "API"
            summary = AndroidHelper.api().toString()
        }
    }

}
