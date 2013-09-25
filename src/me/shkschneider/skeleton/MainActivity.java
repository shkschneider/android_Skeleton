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
import android.view.View;
import android.widget.AdapterView;
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
import me.shkschneider.skeleton.helper.AudioHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.RuntimeHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.SystemHelper;
import me.shkschneider.skeleton.helper.TimeHelper;
import me.shkschneider.skeleton.helper.WebViewHelper;
import me.shkschneider.skeleton.net.NetworkHelper;

@SuppressWarnings("unused")
public class MainActivity extends SherlockListActivity {

    private static final String AUTHOR_NAME = "ShkSchneider";
    private static final String AUTHOR_URL = "https://github.com/shkschneider/android_Skeleton";

    private Boolean mRefreshing;

    private Map<String, String> map(final String key, final String value, final String[] usage) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put("key", key);
        data.put("value", value);
        data.put("usage", String.format("%s\n\nCall: %s\nTakes: %s\nReturns: %s",
                AndroidHelper.packageName(MainActivity.this),
                usage[1],
                usage[2],
                usage[0]
        ));
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

        data.add(map("DEBUG", SkeletonApplication.DEBUG.toString(), new String[] {
                "Boolean", "SkeletonApplication.DEBUG", "-"
        }));
        data.add(map("TAG", SkeletonApplication.TAG, new String[] {
                "String", "SkeletonApplication.TAG", "-"
        }));
        data.add(map("LOCALE", SkeletonApplication.LOCALE, new String[] {
                "Locale", "SkeletonApplication.LOCALE", "-"
        }));
        data.add(map("Time.timestamp()", TimeHelper.timestamp().toString(), new String[] {
                "Long", "TimeHelper.timestamp()", "-"
        }));
        data.add(map("Android.account()", AndroidHelper.account(MainActivity.this), new String[] {
                "String", "AndroidHelper.account()", "Context"
        }));
        data.add(map("Android.tablet()", AndroidHelper.tablet(MainActivity.this).toString(), new String[] {
                "Boolean", "AndroidHelper.tablet()", "Context"
        }));
        data.add(map("Android.id()", AndroidHelper.id(MainActivity.this), new String[] {
                "String", "AndroidHelper.id()", "Context"
        }));
        data.add(map("Android.deviceId()", AndroidHelper.deviceId(MainActivity.this), new String[]{
                "String", "AndroidHelper.deviceId()", "Context"
        }));
        data.add(map("Android.uuid()", AndroidHelper.uuid(MainActivity.this), new String[] {
                "String", "AndroidHelper.uuid()", "Context"
        }));
        data.add(map("Android.randomId()", AndroidHelper.randomId(), new String[] {
                "String", "AndroidHelper.randomId()", "-"
        }));
        data.add(map("Android.codename()", AndroidHelper.codename(), new String[] {
                "String", "AndroidHelper.codename()", "-"
        }));
        data.add(map("Android.manufacturer()", AndroidHelper.manufacturer(), new String[] {
                "String", "AndroidHelper.manufacturer()", "-"
        }));
        data.add(map("Android.device()", AndroidHelper.device(), new String[] {
                "String", "AndroidHelper.device()", "-"
        }));
        data.add(map("Android.release()", AndroidHelper.release(), new String[] {
                "String", "AndroidHelper.release()", "-"
        }));
        data.add(map("Android.api()", AndroidHelper.api().toString(), new String[] {
                "Integer", "AndroidHelper.api()", "-"
        }));
        data.add(map("Android.debug()", AndroidHelper.debug().toString(), new String[] {
                "Boolean", "AndroidHelper.debug()", "-"
        }));
        data.add(map("Android.packageName()", AndroidHelper.packageName(MainActivity.this), new String[] {
                "String", "AndroidHelper.packageName()", "Context"
        }));
        data.add(map("Android.name()", AndroidHelper.name(MainActivity.this), new String[] {
                "String", "AndroidHelper.name()", "Context"
        }));
        data.add(map("Android.versionName()", AndroidHelper.versionName(MainActivity.this), new String[] {
                "String", "AndroidHelper.versionName()", "Context"
        }));
        data.add(map("Android.versionCode()", AndroidHelper.versionCode(MainActivity.this).toString(), new String[] {
                "Integer", "AndroidHelper.versionCode()", "Context"
        }));
        data.add(map("SystemHelper.uname()", SystemHelper.uname(), new String[] {
                "String", "Systemss.uname()", "-"
        }));
        data.add(map("Locale.language()", LocaleHelper.language(), new String[] {
                "String", "LocaleHelper.language()", "-"
        }));
        data.add(map("Locale.language2()", LocaleHelper.language2(), new String[] {
                "String", "LocaleHelper.language2()", "-"
        }));
        data.add(map("Locale.language3()", LocaleHelper.language3(), new String[] {
                "String", "LocaleHelper.language3()", "-"
        }));
        data.add(map("Locale.country()", LocaleHelper.country(), new String[] {
                "String", "LocaleHelper.country()", "-"
        }));
        data.add(map("Locale.country2()", LocaleHelper.country2(), new String[] {
                "String", "LocaleHelper.country2()", "-"
        }));
        data.add(map("Locale.country3()", LocaleHelper.country3(), new String[] {
                "String", "LocaleHelper.country3()", "-"
        }));
        data.add(map("File.internalDir()", FileHelper.internalDir(MainActivity.this), new String[] {
                "String", "FileHelper.internalDir()", "Context"
        }));
        data.add(map("File.externalDir()", FileHelper.externalDir(MainActivity.this), new String[] {
                "String", "FileHelper.externalDir()", "Context"
        }));
        data.add(map("File.internalCacheDir()", FileHelper.internalCacheDir(MainActivity.this), new String[] {
                "String", "FileHelper.internalCacheDir()", "Context"
        }));
        data.add(map("File.externalCacheDir()", FileHelper.externalCacheDir(MainActivity.this), new String[] {
                "String", "FileHelper.externalCacheDir()", "Context"
        }));
        data.add(map("File.downloadCache()", FileHelper.downloadCache(), new String[] {
                "String", "FileHelper.downloadCache()", "-"
        }));
        data.add(map("File.sdCardAvailable()", FileHelper.sdCardAvailable().toString(), new String[] {
                "Boolean", "FileHelper.sdCardAvailable()", "-"
        }));
        data.add(map("File.sdCard()", FileHelper.sdCard(), new String[] {
                "String", "FileHelper.sdCard()", "-"
        }));
        data.add(map("AudioHelper.volume()", AudioHelper.volume(MainActivity.this).toString(), new String[] {
                "Integer", "AudioHelper.volume()", "Context"
        }));
        data.add(map("NetworkHelper.userAgent()", NetworkHelper.userAgent(), new String[] {
                "String", "NetworkHelper.userAgent()", "Context"
        }));
        data.add(map("NetworkHelper.online()", NetworkHelper.online(MainActivity.this).toString(), new String[] {
                "Boolean", "NetworkHelper.online()", "Context"
        }));
        data.add(map("NetworkHelper.wifi()", NetworkHelper.wifi(MainActivity.this).toString(), new String[] {
                "Boolean", "NetworkHelper.wifi()", "Context"
        }));
        data.add(map("NetworkHelper.macAddress()", NetworkHelper.macAddress(MainActivity.this), new String[] {
                "String", "NetworkHelper.macAddress()", "Context"
        }));
        data.add(map("NetworkHelper.ipAddresses()", NetworkHelper.ipAddresses().toString(), new String[] {
                "List<String>", "NetworkHelper.ipAddresses()", "-"
        }));
        data.add(map("Notification.toastShort()", "Context-independent", new String[] {
                "void", "Skeleton.Notification.toastShort()", "Context String"
        }));
        data.add(map("Notification.toastLong()", "Context-independent", new String[] {
                "void", "Skeleton.Notification.toastLong()", "Context String"
        }));
        data.add(map("Notification.croutonInfo()", "Context-dependent", new String[] {
                "void", "Skeleton.Notification.croutonInfo()", "Activity String"
        }));
        data.add(map("Notification.croutonConfirm()", "Context-dependent", new String[] {
                "void", "Skeleton.Notification.croutonConfirm()", "Activity String"
        }));
        data.add(map("Notification.croutonAlert()", "Context-dependent", new String[] {
                "void", "Skeleton.Notification.croutonAlert()", "Activity String"
        }));
        data.add(map("RuntimeHelper.processors()", RuntimeHelper.processors().toString(), new String[] {
                "Integer", "Runtimess.processors()", "-"
        }));
        data.add(map("RuntimeHelper.freeMemory()", RuntimeHelper.freeMemory().toString(), new String[] {
                "Long", "Runtimess.freeMemory()", "-"
        }));
        data.add(map("RuntimeHelper.maxMemory()", RuntimeHelper.maxMemory().toString(), new String[] {
                "Long", "Runtimess.maxMemory()", "-"
        }));
        data.add(map("RuntimeHelper.totalMemory()", RuntimeHelper.totalMemory().toString(), new String[] {
                "Long", "Runtimess.totalMemory()", "-"
        }));
        data.add(map("ScreenHelper.on()", ScreenHelper.on(MainActivity.this).toString(), new String[] {
                "Boolean", "Screenss.on()", "Context"
        }));
        data.add(map("ScreenHelper.density()", ScreenHelper.density(MainActivity.this).toString(), new String[] {
                "Integer", "Screenss.density()", "Context"
        }));
        data.add(map("ScreenHelper.height()", ScreenHelper.height(MainActivity.this).toString(), new String[] {
                "Integer", "Screenss.height()", "Context"
        }));
        data.add(map("ScreenHelper.width()", ScreenHelper.width(MainActivity.this).toString(), new String[] {
                "Integer", "Screenss.width()", "Context"
        }));
        data.add(map("ScreenHelper.orientation()", ScreenHelper.orientation(MainActivity.this).toString(), new String[] {
                "Integer", "Screenss.orientation()", "Context"
        }));
        data.add(map("Activity.showcase()", "Showcase an id (with callback)", new String[] {
                "void", "ActivityHelper.showcase()", "Activity int String String"
        }));

        setListAdapter(new SimpleAdapter(MainActivity.this,
                data,
                android.R.layout.simple_list_item_2,
                new String[] { "key", "value" },
                new int[] { android.R.id.text1, android.R.id.text2 }
        ));

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Map<String, String> map = data.get(position);
                if (map != null) {
                    final String key = map.get("key");
                    LogHelper.v(key);
                    ActivityHelper.alertDialogBuilder(MainActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                            .setTitle(key)
                            .setMessage(map.get("usage"))
                            .setNeutralButton(android.R.string.ok, null)
                            .setCancelable(true)
                            .create()
                            .show();
                }
                else {
                    LogHelper.w("Position invalid");
                }
            }

        });

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
            case R.id.web:
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
