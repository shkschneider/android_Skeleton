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

public class SkeletonActivity extends SherlockListActivity {

    private static final String AUTHOR_NAME = "ShkSchneider";
    private static final String AUTHOR_URL = "https://github.com/shkschneider/android_Skeleton";

    private Boolean mRefreshing;

    private Map<String, String> map(final String key, final String value, final String[] usage) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put("key", key);
        data.put("value", value);
        data.put("usage", String.format("%s\n\nCall: %s\nTakes: %s\nReturns: %s",
                Skeleton.Android.packageName(SkeletonActivity.this),
                usage[1],
                usage[2],
                usage[0]
        ));
        return data;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Skeleton.Activity.indeterminate(SkeletonActivity.this);
        setContentView(R.layout.skeleton);

        mRefreshing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Skeleton.Activity.showcase(SkeletonActivity.this,
                android.R.id.home,
                Skeleton.Android.name(SkeletonActivity.this),
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
        Skeleton.Activity.indeterminate(SkeletonActivity.this, mRefreshing);

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
        data.add(map("Time.timestamp()", Skeleton.Time.timestamp().toString(), new String[] {
                "Long", "Skeleton.Time.timestamp()", "-"
        }));
        data.add(map("Android.account()", Skeleton.Android.account(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.account()", "Context"
        }));
        data.add(map("Android.tablet()", Skeleton.Android.tablet(SkeletonActivity.this).toString(), new String[] {
                "Boolean", "Skeleton.Android.tablet()", "Context"
        }));
        data.add(map("Android.id()", Skeleton.Android.id(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.id()", "Context"
        }));
        data.add(map("Android.deviceId()", Skeleton.Android.deviceId(SkeletonActivity.this), new String[]{
                "String", "Skeleton.Android.deviceId()", "Context"
        }));
        data.add(map("Android.uuid()", Skeleton.Android.uuid(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.uuid()", "Context"
        }));
        data.add(map("Android.randomId()", Skeleton.Android.randomId(), new String[] {
                "String", "Skeleton.Android.randomId()", "-"
        }));
        data.add(map("Android.codename()", Skeleton.Android.codename(), new String[] {
                "String", "Skeleton.Android.codename()", "-"
        }));
        data.add(map("Android.manufacturer()", Skeleton.Android.manufacturer(), new String[] {
                "String", "Skeleton.Android.manufacturer()", "-"
        }));
        data.add(map("Android.device()", Skeleton.Android.device(), new String[] {
                "String", "Skeleton.Android.device()", "-"
        }));
        data.add(map("Android.release()", Skeleton.Android.release(), new String[] {
                "String", "Skeleton.Android.release()", "-"
        }));
        data.add(map("Android.api()", Skeleton.Android.api().toString(), new String[] {
                "Integer", "Skeleton.Android.api()", "-"
        }));
        data.add(map("Android.debug()", Skeleton.Android.debug().toString(), new String[] {
                "Boolean", "Skeleton.Android.debug()", "-"
        }));
        data.add(map("Android.packageName()", Skeleton.Android.packageName(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.packageName()", "Context"
        }));
        data.add(map("Android.name()", Skeleton.Android.name(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.name()", "Context"
        }));
        data.add(map("Android.versionName()", Skeleton.Android.versionName(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Android.versionName()", "Context"
        }));
        data.add(map("Android.versionCode()", Skeleton.Android.versionCode(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Android.versionCode()", "Context"
        }));
        data.add(map("System.uname()", Skeleton.System.uname(), new String[] {
                "String", "Skeleton.System.uname()", "-"
        }));
        data.add(map("Locale.language()", Skeleton.Locale.language(), new String[] {
                "String", "Skeleton.Locale.language()", "-"
        }));
        data.add(map("Locale.language2()", Skeleton.Locale.language2(), new String[] {
                "String", "Skeleton.Locale.language2()", "-"
        }));
        data.add(map("Locale.language3()", Skeleton.Locale.language3(), new String[] {
                "String", "Skeleton.Locale.language3()", "-"
        }));
        data.add(map("Locale.country()", Skeleton.Locale.country(), new String[] {
                "String", "Skeleton.Locale.country()", "-"
        }));
        data.add(map("Locale.country2()", Skeleton.Locale.country2(), new String[] {
                "String", "Skeleton.Locale.country2()", "-"
        }));
        data.add(map("Locale.country3()", Skeleton.Locale.country3(), new String[] {
                "String", "Skeleton.Locale.country3()", "-"
        }));
        data.add(map("File.internalDir()", Skeleton.File.internalDir(SkeletonActivity.this), new String[] {
                "String", "Skeleton.File.internalDir()", "Context"
        }));
        data.add(map("File.externalDir()", Skeleton.File.externalDir(SkeletonActivity.this), new String[] {
                "String", "Skeleton.File.externalDir()", "Context"
        }));
        data.add(map("File.internalCacheDir()", Skeleton.File.internalCacheDir(SkeletonActivity.this), new String[] {
                "String", "Skeleton.File.internalCacheDir()", "Context"
        }));
        data.add(map("File.externalCacheDir()", Skeleton.File.externalCacheDir(SkeletonActivity.this), new String[] {
                "String", "Skeleton.File.externalCacheDir()", "Context"
        }));
        data.add(map("File.downloadCache()", Skeleton.File.downloadCache(), new String[] {
                "String", "Skeleton.File.downloadCache()", "-"
        }));
        data.add(map("File.sdCardAvailable()", Skeleton.File.sdCardAvailable().toString(), new String[] {
                "Boolean", "Skeleton.File.sdCardAvailable()", "-"
        }));
        data.add(map("File.sdCard()", Skeleton.File.sdCard(), new String[] {
                "String", "Skeleton.File.sdCard()", "-"
        }));
        data.add(map("Audio.volume()", Skeleton.Audio.volume(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Audio.volume()", "Context"
        }));
        data.add(map("Network.defaultUserAgent()", Skeleton.Network.defaultUserAgent(), new String[] {
                "String", "Skeleton.Network.defaultUserAgent()", "-"
        }));
        data.add(map("Network.userAgent()", Skeleton.Network.userAgent(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Network.userAgent()", "Context"
        }));
        data.add(map("Network.online()", Skeleton.Network.online(SkeletonActivity.this).toString(), new String[] {
                "Boolean", "Skeleton.Network.online()", "Context"
        }));
        data.add(map("Network.wifi()", Skeleton.Network.wifi(SkeletonActivity.this).toString(), new String[] {
                "Boolean", "Skeleton.Network.wifi()", "Context"
        }));
        data.add(map("Network.macAddress()", Skeleton.Network.macAddress(SkeletonActivity.this), new String[] {
                "String", "Skeleton.Network.macAddress()", "Context"
        }));
        data.add(map("Network.ipAddresses()", Skeleton.Network.ipAddresses().toString(), new String[] {
                "List<String>", "Skeleton.Network.ipAddresses()", "-"
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
        data.add(map("Runtime.processors()", Skeleton.Runtime.processors().toString(), new String[] {
                "Integer", "Skeleton.Runtime.processors()", "-"
        }));
        data.add(map("Runtime.freeMemory()", Skeleton.Runtime.freeMemory().toString(), new String[] {
                "Long", "Skeleton.Runtime.freeMemory()", "-"
        }));
        data.add(map("Runtime.maxMemory()", Skeleton.Runtime.maxMemory().toString(), new String[] {
                "Long", "Skeleton.Runtime.maxMemory()", "-"
        }));
        data.add(map("Runtime.totalMemory()", Skeleton.Runtime.totalMemory().toString(), new String[] {
                "Long", "Skeleton.Runtime.totalMemory()", "-"
        }));
        data.add(map("Screen.isOn()", Skeleton.Screen.isOn(SkeletonActivity.this).toString(), new String[] {
                "Boolean", "Skeleton.Screen.isOn()", "Context"
        }));
        data.add(map("Screen.density()", Skeleton.Screen.density(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Screen.density()", "Context"
        }));
        data.add(map("Screen.height()", Skeleton.Screen.height(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Screen.height()", "Context"
        }));
        data.add(map("Screen.width()", Skeleton.Screen.width(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Screen.width()", "Context"
        }));
        data.add(map("Screen.orientation()", Skeleton.Screen.orientation(SkeletonActivity.this).toString(), new String[] {
                "Integer", "Skeleton.Screen.orientation()", "Context"
        }));
        data.add(map("Activity.showcase()", "Showcase an id (with callback)", new String[] {
                "void", "Skeleton.Activity.showcase()", "Activity int String String"
        }));

        setListAdapter(new SimpleAdapter(SkeletonActivity.this,
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
                    Skeleton.Log.v(key);
                    Skeleton.Notification.alertDialogBuilder(SkeletonActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                            .setTitle(key)
                            .setMessage(map.get("usage"))
                            .setNeutralButton(android.R.string.ok, null)
                            .setCancelable(true)
                            .create()
                            .show();
                }
                else {
                    Skeleton.Log.w("Position invalid");
                }
            }

        });

        mRefreshing = false;
        invalidateOptionsMenu();
        Skeleton.Activity.indeterminate(SkeletonActivity.this, mRefreshing);
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
                startActivity(new Intent(SkeletonActivity.this, SkeletonActivity.class));
                break ;
            case R.id.author:
                Skeleton.Notification.alertDialogBuilder(SkeletonActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle(AUTHOR_NAME)
                        .setMessage(AUTHOR_URL)
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;
            case R.id.license:
                Skeleton.Notification.alertDialogBuilder(SkeletonActivity.this, R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle("Apache 2.0")
                        .setView(Skeleton.WebView.fromHtml(SkeletonActivity.this,
                                Skeleton.File.readString(Skeleton.File.openRaw(SkeletonActivity.this, R.raw.license)).replaceAll("\n", "<br />")
                        ))
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;
            case R.id.web:
                Skeleton.Intent.web(SkeletonActivity.this, AUTHOR_URL);
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
