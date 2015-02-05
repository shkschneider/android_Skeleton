package me.shkschneider.skeleton;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class SkeletonFragmentActivity extends SkeletonActivity {

    protected void setContentFragment(@NonNull final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

}
