package me.shkschneider.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.concurrent.TimeUnit;

import me.shkschneider.app.fragment.AboutFragment;
import me.shkschneider.skeleton.SkeletonFragmentActivity;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.RunnableHelper;

public class AboutActivity extends SkeletonFragmentActivity {

    public static Intent getIntent(final Activity activity) {
        return new Intent(activity, AboutActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        home(true);

        setContentFragment(new AboutFragment());

        refreshable(true, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ActivityHelper.toast("Reloading...");
                RunnableHelper.delayRunnable(new Runnable() {
                    @Override
                    public void run() {
                        loading(false);
                    }
                }, 1, TimeUnit.SECONDS);
            }
        });
    }

}
