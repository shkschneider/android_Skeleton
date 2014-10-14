package me.shkschneider.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.shkschneider.sdk.RunnableHelper;
import me.shkschneider.sdk.MyActivity;
import me.shkschneider.sdk.ActivityHelper;
import me.shkschneider.sdk.IntentHelper;
import me.shkschneider.sdk.MyListView;
import me.shkschneider.sdk.StringHelper;

public class MainActivity extends MyActivity {

    public static Intent intent(final MyActivity myActivity) {
        return new Intent(myActivity, MainActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchable(getResources().getString(R.string.dots), new SearchCallback() {
            @Override
            public void onSearchTextChange(String q) {
                // Ignore
            }

            @Override
            public void onSearchTextSubmit(String q) {
                ActivityHelper.croutonGreen(MainActivity.this, q);
            }
        });

        final MyListView myListView = (MyListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(mAdapter);
        myListView.setCallback(new MyListView.Callback() {
            @Override
            public void overscroll(final int n) {
                if (! loading()) {
                    charging(n);
                }
            }

            @Override
            public void overscroll() {
                refresh();
            }

            @Override
            public void bottom() {
                ActivityHelper.toast("bottom");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        loading(true);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading(false);
                final Locale[] locales = Locale.getAvailableLocales();
                final List<String> countries = new ArrayList<String>();
                for (final Locale locale : locales) {
                    final String country = locale.getDisplayCountry().trim();
                    if (!StringHelper.nullOrEmpty(country) && !countries.contains(country)) {
                        countries.add(country);
                    }
                }
                Collections.shuffle(countries);
                mAdapter.clear();
                mAdapter.addAll(countries);
                mAdapter.notifyDataSetChanged();
            }
        };
        RunnableHelper.delay(runnable, 1, TimeUnit.SECONDS);
//        Ion.with(this)
//                .load("http://ifconfig.me/ip")
//                .asString()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<String>>() {
//                    @Override
//                    public void onCompleted(final Exception e, final Response<String> result) {
//                        loading(false);
//
//                        final int responseCode = result.getHeaders().getResponseCode();
//                        final String responseMessage = result.getHeaders().getResponseMessage();
//                        final String response = result.getResult();
//                    }
//                });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(AboutActivity.intent(MainActivity.this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // ...
    }

    @Override
    protected void onRestoreInstanceState(@NotNull final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // ...
    }

}
