package me.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.jetbrains.annotations.NotNull;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import me.sdk.Activity;
import me.sdk.ActivityHelper;
import me.sdk.IntentHelper;
import me.sdk.LogHelper;

public class MainActivity extends Activity {

    public static Intent intent(final Activity activity) {
        return new Intent(activity, MainActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    private static final String URL = "http://ifconfig.me/ip";

    private SmoothProgressBar mSmoothProgressBar;
    private TextView mTextView;

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
                ActivityHelper.toast(q);
            }
        });
        mSmoothProgressBar = (SmoothProgressBar) findViewById(R.id.smoothprogressbar);
        mSmoothProgressBar.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(MainActivity.this)
                .interpolator(new AccelerateInterpolator())
                .callbacks(new SmoothProgressDrawable.Callbacks() {
                    @Override
                    public void onStop() {
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onStart() {
                        invalidateOptionsMenu();
                    }
                })
                .build());
        ((TextView) findViewById(android.R.id.text1)).setText(URL);
        mTextView = (TextView) findViewById(android.R.id.text2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        mSmoothProgressBar.progressiveStart();
        Ion.with(MainActivity.this)
                .load(URL)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<String> result) {
                        mSmoothProgressBar.progressiveStop();
                        if (e != null) {
                            LogHelper.wtf(e);
                            ActivityHelper.croutonRed(MainActivity.this, e.getLocalizedMessage());
                            return ;
                        }

                        final int responseCode = result.getHeaders().getResponseCode();
                        final String responseMessage = result.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            ActivityHelper.croutonOrange(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
                        }
                        else {
                            ActivityHelper.croutonGreen(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
                        }

                        final String responseData = result.getResult();
                        mTextView.setText(responseData);
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
