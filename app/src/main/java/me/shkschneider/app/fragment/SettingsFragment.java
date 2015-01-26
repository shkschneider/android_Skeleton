package me.shkschneider.app.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.View;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.SkeletonPreferenceFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;

public class SettingsFragment extends SkeletonPreferenceFragment {

    @Override
    public void onCreate(final Bundle paramBundle) {
        super.onCreate(paramBundle);
        addPreferencesFromResource(R.xml.fragment_settings);

        skeletonActivity().refreshable(false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Switch

        final SwitchPreference switchPreference = (SwitchPreference) findPreference("switch");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object key) {
                final boolean oldState = switchPreference.isChecked();
                final boolean newState = ! oldState;
                ActivityHelper.toast(String.format("%s: %s -> %s", switchPreference.getTitle(), oldState, newState));
                return true;
            }
        });

        // Notifications

        final Preference notificationsPreference = findPreference("notifications");
        notificationsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                startActivity(IntentHelper.applicationSettings());
                return true;
            }
        });

        // Google Play

        final Preference googlePlayPreference = findPreference("googleplay");
        googlePlayPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                startActivity(IntentHelper.googlePlay());
                return true;
            }
        });
    }

}
