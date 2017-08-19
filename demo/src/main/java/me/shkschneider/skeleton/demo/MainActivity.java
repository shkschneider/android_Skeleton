package me.shkschneider.skeleton.demo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.demo.data.ShkMod;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.BroadcastHelper;
import me.shkschneider.skeleton.helper.DateTimeHelper;
import me.shkschneider.skeleton.helper.DeviceHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.NotificationHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.java.ObjectHelper;
import me.shkschneider.skeleton.network.Proxy;
import me.shkschneider.skeleton.network.WebService;
import me.shkschneider.skeleton.network.WebServiceException;
import me.shkschneider.skeleton.ui.AnimationHelper;
import me.shkschneider.skeleton.ui.BottomSheet;

/**
 * SkeletonActivity
 * Collapsible Toolbar
 * ViewPager
 * FloatingActionButton
 * -> LocalBroadcast
 * -> Volley request (Proxy)
 * -> Notification (RunnableHelper.delay())
 * -> onNewIntent() (ActivityHelper.toast())
 */
public class MainActivity extends SkeletonActivity {

    private static final String BROADCAST_SECRET = "BROADCAST_SECRET";
    private static final String BROADCAST_SECRET_CODE = "BROADCAST_SECRET_CODE";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar.setTitle("Skeleton");
        mToolbar.setSubtitle("for Android");

        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                switch (position) {
                    case 0:
                        return new ShkFragment();
                    case 1:
                        return new SkFragment();
                }
                return null;
            }
            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case 0:
                        return "ShkSchneider";
                    case 1:
                        return "Skeleton";
                }
                return null;
            }
            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                // Ignore
            }

            @Override
            public void onPageSelected(final int position) {
                LogHelper.verbose("Page: " + position);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                // Ignore
            }

        });

        final FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setImageResource(android.R.drawable.ic_dialog_info);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(BROADCAST_SECRET).putExtra(BROADCAST_SECRET_CODE, 42);
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
            }
        });
        AnimationHelper.revealOn(floatingActionButton);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BroadcastHelper.register(mBroadcastReceiver, new IntentFilter(BROADCAST_SECRET));
    }

    @Override
    protected void onResume() {
        super.onResume();

        DeviceHelper.screenSize();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Proxy.get().getRequestQueue().cancelAll(URL);
        BroadcastHelper.unregister(mBroadcastReceiver);
    }

    private final static String URL = "https://raw.githubusercontent.com/shkschneider/android_manifest/master/VERSION.json";

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("unused")
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final int code = intent.getIntExtra(BROADCAST_SECRET_CODE, 0);
            network();
        }
    };

    private void network() {
        WebService.get(URL)
                .as(ShkMod.class)
                .callback(new WebService.Callback<ShkMod>() {
                    @Override
                    public void success(@Nullable final ShkMod result) {
                        if (result == null) {
                            ActivityHelper.toast(ClassHelper.simpleName(ShkMod.class));
                            return;
                        }
                        notification((int) DateTimeHelper.timestamp(), ClassHelper.simpleName(ShkMod.class), ObjectHelper.jsonify(result));
                    }
                    @Override
                    public void failure(@NonNull final WebServiceException e) {
                        ActivityHelper.toast(e.getClass().getSimpleName());
                    }
                }).run();
//        final String tag = URL; // defaults to URL anyway
//        Proxy.get().getRequestQueue().cancelAll(tag);
//        Proxy.get().getRequestQueue().add(
//                new MyRequest(Request.Method.GET, URL,
//                        new Response.Listener<MyResponse>() {
//                            @Override
//                            public void onResponse(final MyResponse response) {
//                                final ShkMod shkMod = new Gson().fromJson(response.toString(), ShkMod.class);
//                                notification((int) DateTimeHelper.timestamp(), ClassHelper.simpleName(ShkMod.class), ObjectHelper.jsonify(shkMod));
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(final VolleyError error) {
//                                ActivityHelper.toast(error.getClass().getSimpleName());
//                            }
//                        })
//                        .setCacheTimeout((int) TimeUnit.SECONDS.toMillis(10)) // timeout
//                        .setPriority(Request.Priority.NORMAL) // priority
//                        .setRetryPolicy(new DefaultRetryPolicy(2500, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)) // timeout reties
//                        .setTag(tag) // tag
//        );
    }

    private void notification(final int id, final String title, final String message) {
        final Intent intent = new Intent(getBaseContext(), MainActivity.class)
                .putExtra("title", title)
                .putExtra("message", message);
        final NotificationCompat.Builder builder = NotificationHelper.Builder(
                ContextCompat.getColor(getApplicationContext(), R.color.accentColor), ApplicationHelper.DEFAULT_ICON, null,
                "Ticker",
                "Skeleton", "for Android", null, null,
                NotificationHelper.pendingIntent(MainActivity.this, intent));
        NotificationHelper.notify(id, builder);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);

        final String title = intent.getStringExtra("title");
        final String message = intent.getStringExtra("message");
        new BottomSheet.Builder(MainActivity.this)
                .setTitle(title)
                .setContent(message)
                .setPositive(getResources().getString(android.R.string.ok), null)
                .setNegative(getResources().getString(android.R.string.cancel), null)
                .build()
                .show();
    }

}
