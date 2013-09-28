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

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.RuntimeHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.SystemHelper;
import me.shkschneider.skeleton.helper.TimeHelper;
import me.shkschneider.skeleton.helper.WebViewHelper;
import me.shkschneider.skeleton.net.NetworkHelper;
import me.shkschneider.skeleton.task.QueuedTasks;
import me.shkschneider.skeleton.task.Tasks;

@SuppressWarnings("unused")
public class MainActivity extends SherlockListActivity {

    private static final String AUTHOR_NAME = "ShkSchneider";
    private static final String AUTHOR_URL = "https://github.com/shkschneider/android_Skeleton";

    private Map<String, String> map(final String m, final String c, final String message) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put("text1", m);
        data.put("text2", c);
        data.put("title", m);
        data.put("message", message);
        return data;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.indeterminate(MainActivity.this);
        setContentView(R.layout.skeleton);

        ActivityHelper.showcase(MainActivity.this,
                android.R.id.home,
                AndroidHelper.name(MainActivity.this),
                String.format("%s\n%s\n%s\n%s",
                        "This is a skeleton application for Android.",
                        "It features a lot of static classes that could help developers.",
                        "Thanks for downloading!",
                        "Get the code: shkschneider@github!"),
                null);

        final Tasks tasks = new Tasks(MainActivity.this);
        tasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
            }

        });
        tasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
            }

        });
        tasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
                final Boolean b = true;
            }

        });
        tasks.run(new Tasks.Callback() {

            @Override
            public void tasksCallback(final Long duration) {
                ActivityHelper.alertDialogBuilder(MainActivity.this)
                        .setMessage("Tasks: " + duration + " ms")
                        .setNeutralButton(android.R.string.ok, null)
                        .create()
                        .show();
            }

        });

        final QueuedTasks queuedTasks = new QueuedTasks(MainActivity.this);
        queuedTasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
            }

        });
        queuedTasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
            }

        });
        queuedTasks.queue(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    LogHelper.w("InterruptedException: " + e.getMessage());
                }
                final Boolean b = true;
            }

        });
        queuedTasks.run(new QueuedTasks.Callback() {

            @Override
            public void queuedTasksCallback(final Long duration) {
                ActivityHelper.alertDialogBuilder(MainActivity.this)
                        .setMessage("QueuedTasks: " + duration + " ms")
                        .setNeutralButton(android.R.string.ok, null)
                        .create()
                        .show();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityHelper.indeterminate(MainActivity.this, true);

        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        data.add(map("DEBUG", "SkeletonApplication", SkeletonApplication.DEBUG.toString()));
        data.add(map("LOCALE", "SkeletonApplication", SkeletonApplication.LOCALE));
        data.add(map("TAG", "SkeletonApplication", SkeletonApplication.TAG));

        data.add(map("auth()", "authenticator.FacebookAuthenticator", null));
        //data.add(map("auth()", "authenticator.GoogleAuthenticator", null));
        //data.add(map("auth()", "authenticator.SingleSignOnAuthenticator", null));
        //data.add(map("auth()", "authenticator.TwitterAuthenticator", null));

        data.add(map("alertDialogBuilder()", "helper.ActivityHelper", null));
        data.add(map("popup()", "helper.ActivityHelper", null));
        data.add(map("indeterminate()", "helper.ActivityHelper", null));
        data.add(map("showcase()", "helper.ActivityHelper", null));

        data.add(map("account()", "helper.AndroidHelper", AndroidHelper.account(MainActivity.this)));
        data.add(map("api()", "helper.AndroidHelper", String.valueOf(AndroidHelper.api())));
        data.add(map("codename()", "helper.AndroidHelper", AndroidHelper.codename()));
        data.add(map("debug()", "helper.AndroidHelper", String.valueOf(AndroidHelper.debug())));
        data.add(map("device()", "helper.AndroidHelper", AndroidHelper.device()));
        data.add(map("deviceId()", "helper.AndroidHelper", AndroidHelper.deviceId(MainActivity.this)));
        data.add(map("id()", "helper.AndroidHelper", AndroidHelper.id(MainActivity.this)));
        data.add(map("manufacturer()", "helper.AndroidHelper", AndroidHelper.manufacturer()));
        data.add(map("name()", "helper.AndroidHelper", AndroidHelper.name(MainActivity.this)));
        data.add(map("packageName()", "helper.AndroidHelper", AndroidHelper.packageName(MainActivity.this)));
        data.add(map("randomId()", "helper.AndroidHelper", AndroidHelper.randomId()));
        data.add(map("release()", "helper.AndroidHelper", AndroidHelper.release()));
        data.add(map("signature()", "helper.AndroidHelper", AndroidHelper.signature(MainActivity.this).substring(0, 32).concat("...")));
        data.add(map("sim()", "helper.AndroidHelper", null));
        data.add(map("tablet()", "helper.AndroidHelper", String.valueOf(AndroidHelper.tablet(MainActivity.this))));
        data.add(map("uuid()", "helper.AndroidHelper", AndroidHelper.uuid(MainActivity.this)));
        data.add(map("versionCode()", "helper.AndroidHelper", String.valueOf(AndroidHelper.versionCode(MainActivity.this))));
        data.add(map("versionName()", "helper.AndroidHelper", AndroidHelper.versionName(MainActivity.this)));

        data.add(map("play()", "helper.AudioHelper", null));
        data.add(map("volume()", "helper.AudioHelper", null));

        data.add(map("bitmapFromDrawable()", "helper.BitmapHelper", null));
        data.add(map("bitmapFromUri()", "helper.BitmapHelper", null));
        data.add(map("decodeUri()", "helper.BitmapHelper", null));
        data.add(map("rotateBitmap()", "helper.BitmapHelper", null));

        data.add(map("drawableFromBitmap()", "helper.DrawableHelper", null));
        data.add(map("indeterminateDrawable()", "helper.DrawableHelper", null));

        data.add(map("feature()", "helper.FeaturesHelper", null));

        data.add(map("md5()", "helper.HashHelper", null));
        data.add(map("sha()", "helper.HashHelper", null));

        data.add(map("camera()", "helper.IntentHelper", null));
        data.add(map("canHandle()", "helper.IntentHelper", null));
        data.add(map("email()", "helper.IntentHelper", null));
        data.add(map("gallery()", "helper.IntentHelper", null));
        data.add(map("image()", "helper.IntentHelper", null));
        data.add(map("market()", "helper.IntentHelper", null));
        data.add(map("web()", "helper.IntentHelper", null));

        data.add(map("hide()", "helper.KeyboardHelper", null));
        data.add(map("show()", "helper.KeyboardHelper", null));

        data.add(map("country()", "helper.LocaleHelper", LocaleHelper.country()));
        data.add(map("country2()", "helper.LocaleHelper", LocaleHelper.country2()));
        data.add(map("country3()", "helper.LocaleHelper", LocaleHelper.country3()));
        data.add(map("language()", "helper.LocaleHelper", LocaleHelper.language()));
        data.add(map("language2()", "helper.LocaleHelper", LocaleHelper.language2()));
        data.add(map("language3()", "helper.LocaleHelper", LocaleHelper.language3()));
        data.add(map("locale()", "helper.LocaleHelper", LocaleHelper.locale().toString()));

        data.add(map("betterLocation()", "helper.LocationHelper", null));
        data.add(map("degreesFromMeters()", "helper.LocationHelper", null));
        data.add(map("location()", "helper.LocationHelper", null));
        data.add(map("metersFromDegrees()", "helper.LocationHelper", null));
        data.add(map("start()", "helper.LocationHelper", null));
        data.add(map("stop()", "helper.LocationHelper", null));

        data.add(map("d()", "helper.LogHelper", null));
        data.add(map("e()", "helper.LogHelper", null));
        data.add(map("i()", "helper.LogHelper", null));
        data.add(map("v()", "helper.LogHelper", null));
        data.add(map("w()", "helper.LogHelper", null));

        data.add(map("cancel()", "helper.NotificationHelper", null));
        data.add(map("croutonAlert()", "helper.NotificationHelper", null));
        data.add(map("croutonConfirm()", "helper.NotificationHelper", null));
        data.add(map("croutonInfo()", "helper.NotificationHelper", null));
        data.add(map("notification()", "helper.NotificationHelper", null));
        data.add(map("notificationManager()", "helper.NotificationHelper", null));
        data.add(map("notify()", "helper.NotificationHelper", null));
        data.add(map("toastLong()", "helper.NotificationHelper", null));
        data.add(map("toastShort()", "helper.NotificationHelper", null));

        data.add(map("permission()", "helper.PermissionsHelper", null));

        data.add(map("freeMemory()", "helper.RuntimeHelper", String.valueOf(RuntimeHelper.freeMemory())));
        data.add(map("maxMemory()", "helper.RuntimeHelper", String.valueOf(RuntimeHelper.maxMemory())));
        data.add(map("processors()", "helper.RuntimeHelper", String.valueOf(RuntimeHelper.processors())));
        data.add(map("totalMemory()", "helper.RuntimeHelper", String.valueOf(RuntimeHelper.totalMemory())));

        data.add(map("density()", "helper.ScreenHelper", String.valueOf(ScreenHelper.density(MainActivity.this))));
        data.add(map("height()", "helper.ScreenHelper", String.valueOf(ScreenHelper.height(MainActivity.this))));
        data.add(map("on()", "helper.ScreenHelper", String.valueOf(ScreenHelper.on(MainActivity.this))));
        data.add(map("orientation()", "helper.ScreenHelper", String.valueOf(ScreenHelper.orientation(MainActivity.this))));
        data.add(map("pixelsFromDp()", "helper.ScreenHelper", null));
        data.add(map("wakeLock()", "helper.ScreenHelper", null));
        data.add(map("width()", "helper.ScreenHelper", String.valueOf(ScreenHelper.width(MainActivity.this))));

        data.add(map("capitalize()", "helper.StringHelper", null));
        data.add(map("contains()", "helper.StringHelper", null));
        data.add(map("numeric()", "helper.StringHelper", null));

        data.add(map("systemProperty()", "helper.SystemHelper", null));
        data.add(map("systemService()", "helper.SystemHelper", null));
        data.add(map("uname()", "helper.SystemHelper", SystemHelper.uname()));

        data.add(map("relative()", "helper.TimeHelper", null));
        data.add(map("timestamp()", "helper.TimeHelper", String.valueOf(TimeHelper.timestamp())));

        data.add(map("vibrate()", "helper.VibratorHelper", null));

        data.add(map("back()", "helper.WebViewHelper", null));
        data.add(map("forward()", "helper.WebViewHelper", null));
        data.add(map("fromAsset()", "helper.WebViewHelper", null));
        data.add(map("fromHtml()", "helper.WebViewHelper", null));
        data.add(map("fromUri()", "helper.WebViewHelper", null));
        data.add(map("javascriptInterface()", "helper.WebViewHelper", null));

        data.add(map("cache()", "net.ImageDownloader", null));
        data.add(map("run()", "net.ImageDownloader", null));

        data.add(map("getJsonArray()", "net.JsonParser", null));
        data.add(map("getJsonObject()", "net.JsonParser", null));
        data.add(map("getString()", "net.JsonParser", null));
        data.add(map("parse()", "net.JsonParser", null));

        data.add(map("ipAddresses()", "net.NetworkHelper", NetworkHelper.ipAddresses().toString()));
        data.add(map("macAddress()", "net.NetworkHelper", NetworkHelper.macAddress(MainActivity.this)));
        data.add(map("online()", "net.NetworkHelper", String.valueOf(NetworkHelper.online(MainActivity.this))));
        data.add(map("userAgent()", "net.NetworkHelper", NetworkHelper.userAgent()));
        data.add(map("validUrl()", "net.NetworkHelper", null));
        data.add(map("wifi()", "net.NetworkHelper", String.valueOf(NetworkHelper.wifi(MainActivity.this))));

        data.add(map("cancel()", "net.WebService", null));
        data.add(map("run()", "net.WebService", null));

        Collections.sort(data, new Comparator<Map<String, String>>() {

            @Override
            public int compare(final Map<String, String> m1, final Map<String, String> m2) {
                return m1.get("text1").toUpperCase().compareTo(m2.get("text1").toUpperCase());
            }

        });

        setListAdapter(new MyListAdapter(MainActivity.this, data));

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                final String title = data.get(position).get("title");
                final String message = data.get(position).get("message");
                if (!TextUtils.isEmpty(message)) {
                    ActivityHelper.alertDialogBuilder(MainActivity.this)
                            .setTitle(title)
                            .setMessage(message)
                            .setNeutralButton(android.R.string.ok, null)
                            .setCancelable(true)
                            .create()
                            .show();
                }
            }

        });
        getListView().setFastScrollEnabled(true);
        getListView().smoothScrollToPosition(0);

        ActivityHelper.indeterminate(MainActivity.this, false);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getSupportMenuInflater().inflate(R.menu.skeleton, menu);

        final SearchManager searchManager = (SearchManager) SystemHelper.systemService(MainActivity.this, SystemHelper.SYSTEM_SERVICE_SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextChange(final String newText) {
                    ((MyListAdapter) getListAdapter()).getFilter().filter(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(final String query) {
                    ((MyListAdapter) getListAdapter()).getFilter().filter(query);
                    return true;
                }

            };
            searchView.setOnQueryTextListener(onQueryTextListener);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                final EditText editText = (EditText) item.getActionView();
                if (editText != null) {
                    editText.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void afterTextChanged(final Editable s) {
                        }

                        @Override
                        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
                        }

                        @Override
                        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                            // your search logic here
                        }

                    });
                    editText.requestFocus();
                    KeyboardHelper.show(MainActivity.this);
                }
                else {
                    LogHelper.w("EditText was NULL");
                }
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
