package me.shkschneider.skeleton.demo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.SkeletonActivity;
import me.shkschneider.skeleton.demo.data.ShkMod;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.java.ObjectHelper;
import me.shkschneider.skeleton.network.MyRequest;
import me.shkschneider.skeleton.network.MyResponse;
import me.shkschneider.skeleton.network.Proxy;

public class MainActivity extends SkeletonActivity {

    private static final String BROADCAST_SECRET = "BROADCAST_SECRET";
    private static final String BROADCAST_SECRET_CODE = "BROADCAST_SECRET_CODE";

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar.setTitle("Skeleton");
        mToolbar.setSubtitle("for Android");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
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
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setImageResource(android.R.drawable.ic_dialog_info);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(BROADCAST_SECRET).putExtra(BROADCAST_SECRET_CODE, 42);
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mBroadcastReceiver, new IntentFilter(BROADCAST_SECRET));
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mBroadcastReceiver);
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

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        private final static String URL = "https://raw.githubusercontent.com/shkschneider/android_manifest/master/VERSION.json";
        @SuppressWarnings("unused")
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final int code = intent.getIntExtra(BROADCAST_SECRET_CODE, 0);
            final String tag = "ShkMod"; // defaults to URL
            Proxy.getInstance().getRequestQueue().cancelAll(tag);
            Proxy.getInstance().getRequestQueue().add(
                    new MyRequest(Request.Method.GET, URL,
                            new Response.Listener<MyResponse>() {
                                @Override
                                public void onResponse(final MyResponse response) {
                                    try {
                                        final ShkMod shkMod = new Gson().fromJson(response.toString(), ShkMod.class);
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle(ShkMod.class.getSimpleName())
                                                .setMessage(ObjectHelper.jsonify(shkMod))
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, final int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                    catch (final Exception e) {
                                        LogHelper.wtf(e);
                                        ActivityHelper.toast(e.getClass().getSimpleName());
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(final VolleyError error) {
                                    ActivityHelper.toast(error.getClass().getSimpleName());
                                }
                            })
                            .setCacheTimeout((int) TimeUnit.SECONDS.toMillis(10)) // timeout
                            .setPriority(Request.Priority.NORMAL) // priority
                            .setRetryPolicy(new DefaultRetryPolicy(2500, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)) // timeout reties
                            .setTag(tag) // tag
            );
        }
    };

}
