package me.app;

import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.preference.PreferenceFragment;

import me.sdk.AndroidHelper;
import me.sdk.ApplicationHelper;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.settings);

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
