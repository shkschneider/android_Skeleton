package me.shkschneider.skeleton;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.LogHelper;

/**
 * Base FragmentActivity you should use!
 *
 * - void setFragment(Fragment)
 *
 * @see me.shkschneider.skeleton.SkeletonActivity
 */
public class SkeletonFragmentActivity extends SkeletonActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }

    protected void setFragment(final Fragment fragment) {
        if (fragment == null) {
            LogHelper.warning("Fragment was NULL");
            return ;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }

}
