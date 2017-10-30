package me.shkschneider.skeleton.demo

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View

import me.shkschneider.skeleton.SkeletonActivity
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.java.StringHelper

class AboutActivity : SkeletonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        home(true)
        mToolbar?.title = "Skeleton"
        mToolbar?.subtitle = "for Android"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class AboutFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(bundle: Bundle, rootKey: String) {
            addPreferencesFromResource(R.xml.about)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Application

            val appPackage = findPreference("app_package")
            appPackage.title = "PackageName"
            appPackage.summary = ApplicationHelper.packageName()
            val appVersionName = findPreference("app_versionName")
            appVersionName.title = "VersionName"
            appVersionName.summary = ApplicationHelper.versionName()
            val appVersionCode = findPreference("app_versionCode")
            appVersionCode.title = "VersionCode"
            appVersionCode.summary = ApplicationHelper.versionCode().toString()
            val appVariant = findPreference("app_variant")
            appVariant.title = "Variant"
            appVariant.summary = StringHelper.capitalize(if (ApplicationHelper.debuggable()) "DEBUG" else "RELEASE")
            val appFlavor = findPreference("app_flavor")
            appFlavor.title = "Flavor"
            appFlavor.summary = StringHelper.capitalize(BuildConfig.FLAVOR)

            // OS

            val osVersion = findPreference("os_version")
            osVersion.title = "Version"
            osVersion.summary = AndroidHelper.codename()
            val osApi = findPreference("os_api")
            osApi.title = "API"
            osApi.summary = AndroidHelper.api().toString()
        }

    }

}
