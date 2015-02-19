package me.shkschneider.skeleton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class SkeletonFragmentActivity extends SkeletonActivity {

    protected void setContentFragment(@NonNull final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

}
