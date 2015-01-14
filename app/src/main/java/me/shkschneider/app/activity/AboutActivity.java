package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.shkschneider.app.fragment.AboutFragment;
import me.shkschneider.skeleton.SkeletonFragmentActivity;

public class AboutActivity extends SkeletonFragmentActivity {

    public static Intent getIntent(final Activity activity) {
        return new Intent(activity, AboutActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home(true);

        setFragment(new AboutFragment());
    }

}
