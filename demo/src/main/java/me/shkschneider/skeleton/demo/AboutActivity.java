package me.shkschneider.skeleton.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;

public class AboutActivity extends SkeletonActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        home(true);

        mToolbar.setTitle("Skeleton");
        mToolbar.setSubtitle("for Android");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static class AboutFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(final Bundle bundle, final String rootKey) {
            addPreferencesFromResource(R.xml.about);
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

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

            final Preference appDebug = findPreference("app_debug");
            appDebug.setTitle("debug");
            appDebug.setSummary(String.valueOf(ApplicationHelper.debug()));

            // OS

            final Preference osVersion = findPreference("os_version");
            osVersion.setTitle("version");
            osVersion.setSummary(AndroidHelper.codename());

            final Preference osApi = findPreference("os_api");
            osApi.setTitle("api");
            osApi.setSummary(String.valueOf(AndroidHelper.api()));
        }

    }

}
