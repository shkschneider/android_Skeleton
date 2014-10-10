package me.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import me.sdk.Activity;

public class AboutActivity extends Activity {

    public static Intent intent(final Activity activity) {
        return new Intent(activity, AboutActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, new AboutFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(MainActivity.intent(AboutActivity.this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
