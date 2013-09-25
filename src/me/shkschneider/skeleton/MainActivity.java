/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.WebViewHelper;

@SuppressWarnings("unused")
public class MainActivity extends SherlockListActivity {

    private static final String AUTHOR_NAME = "ShkSchneider";
    private static final String AUTHOR_URL = "https://github.com/shkschneider/android_Skeleton";

    private Boolean mRefreshing;

    private Map<String, String> map(final String key, final String value) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put("key", key);
        data.put("value", value);
        return data;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.indeterminate(MainActivity.this);
        setContentView(R.layout.skeleton);

        mRefreshing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityHelper.showcase(MainActivity.this,
                android.R.id.home,
                AndroidHelper.name(MainActivity.this),
                String.format("%s\n%s\n%s\n%s",
                        "This is a skeleton application for Android.",
                        "It features a lot of static classes that could help developers.",
                        "Thanks for downloading!",
                        "Get the code: shkschneider@github!"),
                null);

        refresh();
    }

    private void refresh() {
        mRefreshing = true;
        invalidateOptionsMenu();
        ActivityHelper.indeterminate(MainActivity.this, mRefreshing);

        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        data.add(map("new FacebookAuthentication(...).auth(...)", "-"));

        setListAdapter(new SimpleAdapter(MainActivity.this,
                data,
                android.R.layout.simple_list_item_2,
                new String[] { "key", "value" },
                new int[] { android.R.id.text1, android.R.id.text2 }
        ));

        mRefreshing = false;
        invalidateOptionsMenu();
        ActivityHelper.indeterminate(MainActivity.this, mRefreshing);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mRefreshing) {
            return false;
        }
        getSupportMenuInflater().inflate(R.menu.skeleton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break ;
            case R.id.author:
                ActivityHelper.alertDialogBuilder(MainActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle(AUTHOR_NAME)
                        .setMessage(AUTHOR_URL)
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;
            case R.id.license:
                ActivityHelper.alertDialogBuilder(MainActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle("Apache 2.0")
                        .setView(WebViewHelper.fromHtml(MainActivity.this,
                                FileHelper.readString(FileHelper.openRaw(MainActivity.this, R.raw.license)).replaceAll("\n", "<br />")
                        ))
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;
            case R.id.code:
                IntentHelper.web(MainActivity.this, AUTHOR_URL);
                break ;
            case R.id.refresh:
                refresh();
                break ;
            default:
                super.onOptionsItemSelected(item);
                break ;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
