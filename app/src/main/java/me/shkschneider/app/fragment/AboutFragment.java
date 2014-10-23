package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.preference.Preference;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonPreferenceFragment;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class AboutFragment extends SkeletonPreferenceFragment {

    @Override
    public void onCreate(final Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.fragment_about);

        // Application

        final Preference appPackage = findPreference("app_package");
        appPackage.setTitle("packageName");
        appPackage.setSummary(ApplicationHelper.packageName());

        final Preference appVersionName = findPreference("app_versionName");
        appVersionName.setTitle("versionName");
        appVersionName.setSummary(ApplicationHelper.versionName());

        final Preference appVersionCode = findPreference("app_versionCode");
        appVersionCode.setTitle("versionCode");
        appVersionCode.setSummary(String.valueOf(ApplicationHelper.versionCode()));

        // OS

        final Preference osVersion = findPreference("os_version");
        osVersion.setTitle("version");
        osVersion.setSummary(AndroidHelper.versionName());

        final Preference osApi = findPreference("os_api");
        osApi.setTitle("api");
        osApi.setSummary(String.valueOf(AndroidHelper.api()));
    }

}
