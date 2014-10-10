package me.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.jetbrains.annotations.NotNull;

import me.sdk.Activity;
import me.sdk.ActivityHelper;
import me.sdk.GsonParser;
import me.sdk.IntentHelper;
import me.sdk.ListView;
import me.sdk.LogHelper;
import me.sdk.StringHelper;

public class MainActivity extends Activity {

    public static Intent intent(final Activity activity) {
        return new Intent(activity, MainActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    private static final String URL = "http://ifconfig.me/all.json";

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

        final ListView listView = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                final View view = super.getView(position, convertView, parent);
                final String item = getItem(position);
                final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(item);
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 0, item.indexOf(" "), 0);
                ((TextView) view.findViewById(android.R.id.text1)).setText(spannableStringBuilder);
                return view;
            }
        };
        listView.setAdapter(mAdapter);
        listView.setCallback(new ListView.Callback() {
            @Override
            public void overscrolledTop() {
                refresh();
            }

            @Override
            public void overscrolledBottom() {
                // Ignore
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
        Ion.with(MainActivity.this)
                .load(URL)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonObject> result) {
                        loading(false);
                        if (e != null) {
                            LogHelper.wtf(e);
                            ActivityHelper.croutonRed(MainActivity.this, e.getLocalizedMessage());
                            return;
                        }

                        final int responseCode = result.getHeaders().getResponseCode();
                        final String responseMessage = result.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            ActivityHelper.croutonOrange(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
                        } else {
                            ActivityHelper.croutonGreen(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
                        }

                        final JsonObject jsonObject = result.getResult();
                        for (final String key : GsonParser.keys(jsonObject)) {
                            final String value = GsonParser.string(jsonObject, key);
                            if (! StringHelper.nullOrEmpty(value)) {
                                mAdapter.add(key + " " + value);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
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
