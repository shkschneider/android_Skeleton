package me.shkschneider.skeleton;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.Window;

import me.shkschneider.skeleton.helpers.AndroidHelper;

@SuppressWarnings({"unused", "deprecation"})
public abstract class SkeletonPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (AndroidHelper.api() >= AndroidHelper.API_11) {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        }
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        // You need to extend this class and call addPreferencesFromResource() yourself
    }

    @TargetApi(AndroidHelper.API_11)
    public void actionbar(final boolean b) {
        final ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }

        if (b) {
            getActionBar().show();
        }
        else {
            getActionBar().hide();
        }
    }

    @TargetApi(AndroidHelper.API_11)
    public void home(final boolean b) {
        final ActionBar actionBar = getActionBar();
        if (actionBar == null) {
            return;
        }

        getActionBar().setDisplayHomeAsUpEnabled(b);
    }

    public void addPreferencesFromResource(final int id) {
        super.addPreferencesFromResource(id);
    }

    public Preference findPreference(final String id) {
        return super.findPreference(id);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void loading(final boolean b) {
        setProgressBarIndeterminate(b);
    }

}
