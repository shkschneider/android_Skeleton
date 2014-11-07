package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.shkschneider.app.fragment.SettingsFragment;
import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.SkeletonFragmentActivity;

public class SettingsActivity extends SkeletonFragmentActivity implements SkeletonActivity.NavigationCallback {

    public static Intent getInstance(final Activity activity) {
        return new Intent(activity, SettingsActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home(true);
        logo(false);

        setFragment(new SettingsFragment());
    }

    @Override
    public void onHomeAsUpPressed() {
        startActivity(DashboardActivity.getInstance(SettingsActivity.this));
    }

}
