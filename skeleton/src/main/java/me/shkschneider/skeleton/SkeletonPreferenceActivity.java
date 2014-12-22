package me.shkschneider.skeleton;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

@SuppressWarnings({"unused", "deprecation"})
public abstract class SkeletonPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // FIXME getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        // FIXME  requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        // You need to extend this class and call addPreferencesFromResource() yourself
    }

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
