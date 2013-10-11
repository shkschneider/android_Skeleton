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

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.github.espiandev.showcaseview.ShowcaseView;

import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.HashHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.KeyboardHelper;
import me.shkschneider.skeleton.helper.LocaleHelper;
import me.shkschneider.skeleton.helper.LocationHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.NotificationHelper;
import me.shkschneider.skeleton.helper.NumberHelper;
import me.shkschneider.skeleton.helper.RuntimeHelper;
import me.shkschneider.skeleton.helper.ScreenHelper;
import me.shkschneider.skeleton.helper.StringHelper;
import me.shkschneider.skeleton.helper.SystemHelper;
import me.shkschneider.skeleton.helper.TimeHelper;
import me.shkschneider.skeleton.helper.VibratorHelper;
import me.shkschneider.skeleton.helper.WebViewHelper;
import me.shkschneider.skeleton.net.NetworkHelper;
import me.shkschneider.skeleton.storage.ExternalStorageHelper;
import me.shkschneider.skeleton.storage.InternalStorageHelper;

@SuppressWarnings("unused")
public class MainActivity extends SherlockListActivity {

    private static final String AUTHOR_NAME = "ShkSchneider";
    private static final String AUTHOR_URL = "https://github.com/shkschneider/android_Skeleton";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(me.shkschneider.skeleton.R.layout.skeleton);

        final ShowcaseView.ConfigOptions configOptions = new ShowcaseView.ConfigOptions();
        final ShowcaseView showcaseView = ShowcaseView.insertShowcaseView(android.R.id.home, MainActivity.this,
                AndroidHelper.name(MainActivity.this),
                String.format("%s\n%s\n%s\n%s",
                        "This is a skeleton application for Android.",
                        "It features a lot of static classes that could help developers.",
                        "Thanks for downloading!",
                        "Get the code: shkschneider@github!"),
                configOptions);
        showcaseView.setBackgroundColor(Color.parseColor("#AA000000"));
        showcaseView.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final MyListAdapter myListAdapter = new MyListAdapter(MainActivity.this, android.R.layout.simple_list_item_2, new MyListAdapter.Data[] {
                new MyListAdapter.Data("SkeletonApplication.DEBUG", SkeletonApplication.DEBUG.toString()),
                new MyListAdapter.Data("SkeletonApplication.LOCALE", SkeletonApplication.LOCALE),
                new MyListAdapter.Data("SkeletonApplication.TAG", SkeletonApplication.TAG),
                // authenticator
                new MyListAdapter.Data("FacebookAuthenticator.auth()", null),
                new MyListAdapter.Data("GoogleAuthenticator.auth()", null),
                new MyListAdapter.Data("SingleSignOnAuthenticator.auth()", null),
                new MyListAdapter.Data("TwitterAuthenticator.auth()", null),
                // helper
                new MyListAdapter.Data("Activity.alertDialogBuilder()", new Runnable() {

                    @Override
                    public void run() {
                        ActivityHelper.alertDialogBuilder(MainActivity.this)
                                .setTitle(AndroidHelper.name(MainActivity.this))
                                .setMessage(AndroidHelper.packageName(MainActivity.this))
                                .setNeutralButton(android.R.string.ok, null)
                                .create()
                                .show();
                    }

                }),
                new MyListAdapter.Data("Activity.popup()", new Runnable() {

                    @Override
                    public void run() {
                        ActivityHelper.popup(MainActivity.this, AndroidHelper.name(MainActivity.this));
                    }

                }),
                new MyListAdapter.Data("Activity.indeterminate()", null),
                new MyListAdapter.Data("Activity.showcase()", null),
                new MyListAdapter.Data("Android.account()", AndroidHelper.account(MainActivity.this)),
                new MyListAdapter.Data("Android.api()", AndroidHelper.api()),
                new MyListAdapter.Data("Android.codename()", AndroidHelper.codename()),
                new MyListAdapter.Data("Android.debug()", AndroidHelper.debug()),
                new MyListAdapter.Data("Android.device()", AndroidHelper.device()),
                new MyListAdapter.Data("Android.deviceId()", AndroidHelper.deviceId(MainActivity.this)),
                new MyListAdapter.Data("Android.id()", AndroidHelper.id(MainActivity.this)),
                new MyListAdapter.Data("Android.manufacturer()", AndroidHelper.manufacturer()),
                new MyListAdapter.Data("Android.name()", AndroidHelper.name(MainActivity.this)),
                new MyListAdapter.Data("Android.packageName()", AndroidHelper.packageName(MainActivity.this)),
                new MyListAdapter.Data("Android.versionCode()", AndroidHelper.versionCode(MainActivity.this)),
                new MyListAdapter.Data("Android.versionName()", AndroidHelper.versionName(MainActivity.this)),
                new MyListAdapter.Data("Android.icon()", null),
                new MyListAdapter.Data("Android.permissions()", new Runnable() {

                    @Override
                    public void run() {
                        String permissions = "";
                        for (final String permission : AndroidHelper.permissions(MainActivity.this)) {
                            permissions = permissions.concat(permission.replace("android.permission.", "")).concat("\n");
                        }
                        ActivityHelper.popup(MainActivity.this, permissions);
                    }

                }),
                new MyListAdapter.Data("Android.randomId()", AndroidHelper.randomId()),
                new MyListAdapter.Data("Android.release()", AndroidHelper.release()),
                new MyListAdapter.Data("Android.signature()", AndroidHelper.signature(MainActivity.this).substring(0, 32)),
                new MyListAdapter.Data("Android.sim()", AndroidHelper.sim(MainActivity.this).toString()),
                new MyListAdapter.Data("Android.tablet()", AndroidHelper.tablet(MainActivity.this)),
                new MyListAdapter.Data("Android.uuid()", AndroidHelper.uuid(MainActivity.this)),
                new MyListAdapter.Data("Audio.play()", null),
                new MyListAdapter.Data("Audio.volume()", null),
                new MyListAdapter.Data("Bitmap.bitmapFromDrawable()", null),
                new MyListAdapter.Data("Bitmap.bitmapFromUri()", null),
                new MyListAdapter.Data("Bitmap.decodeUri()", null),
                new MyListAdapter.Data("Bitmap.rotateBitmap()", null),
                new MyListAdapter.Data("Drawable.drawableFromBitmap()", null),
                new MyListAdapter.Data("Drawable.indeterminateDrawable()", null),
                new MyListAdapter.Data("Features.feature()", null),
                new MyListAdapter.Data("Hash.md5()", HashHelper.md5(AndroidHelper.name(MainActivity.this))),
                new MyListAdapter.Data("Hash.sha()", HashHelper.sha(AndroidHelper.name(MainActivity.this))),
                new MyListAdapter.Data("Intent.camera()", new Runnable() {

                    @Override
                    public void run() {
                        IntentHelper.camera(MainActivity.this);
                    }

                }),
                new MyListAdapter.Data("Intent.canHandle()", null),
                new MyListAdapter.Data("Intent.email()", new Runnable() {

                    @Override
                    public void run() {
                        final String subject = String.format("%s v%s: ",
                                AndroidHelper.name(MainActivity.this),
                                AndroidHelper.versionName(MainActivity.this));
                        IntentHelper.email(MainActivity.this, new String[]{""}, subject, "...");
                    }

                }),
                new MyListAdapter.Data("Intent.gallery()", new Runnable() {

                    @Override
                    public void run() {
                        IntentHelper.gallery(MainActivity.this);
                    }

                }),
                new MyListAdapter.Data("Intent.image()", null),
                new MyListAdapter.Data("Intent.market()", new Runnable() {

                    @Override
                    public void run() {
                        IntentHelper.market(MainActivity.this);
                    }

                }),
                new MyListAdapter.Data("Intent.web()", new Runnable() {

                    @Override
                    public void run() {
                        IntentHelper.web(MainActivity.this, "https://shkschneider.me");
                    }

                }),
                new MyListAdapter.Data("Keyboard.hide()", new Runnable() {

                    @Override
                    public void run() {
                        KeyboardHelper.hide(MainActivity.this);
                    }

                }),
                new MyListAdapter.Data("Keyboard.show()", new Runnable() {

                    @Override
                    public void run() {
                        KeyboardHelper.show(MainActivity.this);
                    }

                }),
                new MyListAdapter.Data("Locale.country()", LocaleHelper.country()),
                new MyListAdapter.Data("Locale.country2()", LocaleHelper.country2()),
                new MyListAdapter.Data("Locale.country3()", LocaleHelper.country3()),
                new MyListAdapter.Data("Locale.language()", LocaleHelper.language()),
                new MyListAdapter.Data("Locale.language2()", LocaleHelper.language2()),
                new MyListAdapter.Data("Locale.language3()", LocaleHelper.language3()),
                new MyListAdapter.Data("Locale.locale()", LocaleHelper.locale()),
                new MyListAdapter.Data("Location.betterLocation()", null),
                new MyListAdapter.Data("Location.degreesFromMeters()", LocationHelper.degreesFromMeters(1F)),
                new MyListAdapter.Data("Location.location()", null),
                new MyListAdapter.Data("Location.metersFromDegrees()", LocationHelper.metersFromDegrees(1F)),
                new MyListAdapter.Data("Location.start()", null),
                new MyListAdapter.Data("Location.stop()", null),
                new MyListAdapter.Data("Log.tag()", LogHelper.tag()),
                new MyListAdapter.Data("Log.level()", LogHelper.level()),
                new MyListAdapter.Data("Log.loggable()", LogHelper.loggable(LogHelper.level())),
                new MyListAdapter.Data("Log.verbose()", null),
                new MyListAdapter.Data("Log.v()", null),
                new MyListAdapter.Data("Log.debug()", null),
                new MyListAdapter.Data("Log.d()", null),
                new MyListAdapter.Data("Log.info()", null),
                new MyListAdapter.Data("Log.i()", null),
                new MyListAdapter.Data("Log.warning()", null),
                new MyListAdapter.Data("Log.w()", null),
                new MyListAdapter.Data("Log.error()", null),
                new MyListAdapter.Data("Log.e()", null),
                new MyListAdapter.Data("Notification.cancel()", null),
                new MyListAdapter.Data("Notification.croutonAlert()", null),
                new MyListAdapter.Data("Notification.croutonConfirm()", null),
                new MyListAdapter.Data("Notification.croutonInfo()", null),
                new MyListAdapter.Data("Notification.notification()", null),
                new MyListAdapter.Data("Notification.notificationManager()", null),
                new MyListAdapter.Data("Notification.notify()", null),
                new MyListAdapter.Data("Notification.toastLong()", new Runnable() {

                    @Override
                    public void run() {
                        NotificationHelper.toastLong(MainActivity.this, AndroidHelper.name(MainActivity.this));
                    }

                }),
                new MyListAdapter.Data("Notification.toastShort()", new Runnable() {

                    @Override
                    public void run() {
                        NotificationHelper.toastShort(MainActivity.this, AndroidHelper.name(MainActivity.this));
                    }

                }),
                new MyListAdapter.Data("Number.random()", NumberHelper.random()),
                new MyListAdapter.Data("Permissions.permission()", null),
                new MyListAdapter.Data("Runtime.freeMemory()", RuntimeHelper.freeMemory()),
                new MyListAdapter.Data("Runtime.maxMemory()", RuntimeHelper.maxMemory()),
                new MyListAdapter.Data("Runtime.processors()", RuntimeHelper.processors()),
                new MyListAdapter.Data("Runtime.totalMemory()", RuntimeHelper.totalMemory()),
                new MyListAdapter.Data("Screen.density()", ScreenHelper.density(MainActivity.this)),
                new MyListAdapter.Data("Screen.height()", ScreenHelper.height(MainActivity.this)),
                new MyListAdapter.Data("Screen.on()", ScreenHelper.on(MainActivity.this)),
                new MyListAdapter.Data("Screen.orientation()", ScreenHelper.orientation(MainActivity.this)),
                new MyListAdapter.Data("Screen.pixelsFromDp()", ScreenHelper.pixelsFromDp(MainActivity.this, 1F)),
                new MyListAdapter.Data("Screen.wakeLock()", null),
                new MyListAdapter.Data("Screen.width()", ScreenHelper.width(MainActivity.this)),
                new MyListAdapter.Data("String.capitalize()", StringHelper.capitalize(AndroidHelper.name(MainActivity.this))),
                new MyListAdapter.Data("String.contains()", null),
                new MyListAdapter.Data("String.numeric()", null),
                new MyListAdapter.Data("System.systemProperty()", null),
                new MyListAdapter.Data("System.systemService()", null),
                new MyListAdapter.Data("System.uname()", SystemHelper.uname()),
                new MyListAdapter.Data("Time.relative()", TimeHelper.relative(TimeHelper.millitimestamp() - 42 * DateUtils.SECOND_IN_MILLIS, TimeHelper.millitimestamp())),
                new MyListAdapter.Data("Time.timestamp()", TimeHelper.timestamp()),
                new MyListAdapter.Data("Vibrator.vibrate()", new Runnable() {

                    @Override
                    public void run() {
                        VibratorHelper.vibrate(MainActivity.this, 1 * DateUtils.SECOND_IN_MILLIS);
                    }

                }),
                new MyListAdapter.Data("WebView.back()", null),
                new MyListAdapter.Data("WebView.forward()", null),
                new MyListAdapter.Data("WebView.fromAsset()", null),
                new MyListAdapter.Data("WebView.fromHtml()", null),
                new MyListAdapter.Data("WebView.fromUri()", null),
                new MyListAdapter.Data("WebView.javascriptInterface()", null),
                // net
                new MyListAdapter.Data("ImageDownloader.cache()", null),
                new MyListAdapter.Data("ImageDownloader.run()", null),
                new MyListAdapter.Data("JsonParser.jsonArray()", null),
                new MyListAdapter.Data("JsonParser.jsonObject()", null),
                new MyListAdapter.Data("JsonParser.string()", null),
                new MyListAdapter.Data("JsonParser.parse()", null),
                new MyListAdapter.Data("Network.ipAddresses()", NetworkHelper.ipAddresses()),
                new MyListAdapter.Data("Network.macAddress()", NetworkHelper.macAddress(MainActivity.this)),
                new MyListAdapter.Data("Network.online()", NetworkHelper.online(MainActivity.this)),
                new MyListAdapter.Data("Network.userAgent()", NetworkHelper.userAgent()),
                new MyListAdapter.Data("Network.validUrl()", null),
                new MyListAdapter.Data("Network.wifi()", NetworkHelper.wifi(MainActivity.this)),
                new MyListAdapter.Data("WebService.cancel()", null),
                new MyListAdapter.Data("WebService.run()", null),
                // storage
                new MyListAdapter.Data("ExternalStorage.available()", ExternalStorageHelper.available()),
                new MyListAdapter.Data("ExternalStorage.read()", null),
                new MyListAdapter.Data("ExternalStorage.write()", null),
                new MyListAdapter.Data("InternalStorage.available()", InternalStorageHelper.available()),
                new MyListAdapter.Data("InternalStorage.read()", null),
                new MyListAdapter.Data("InternalStorage.write()", null),
                new MyListAdapter.Data("SharedPreferences.get()", null),
                new MyListAdapter.Data("SharedPreferences.put()", null),
                new MyListAdapter.Data("Sqlite.add()", null),
                new MyListAdapter.Data("Sqlite.count()", null),
                new MyListAdapter.Data("Sqlite.get()", null),
                new MyListAdapter.Data("Sqlite.remove()", null),
                new MyListAdapter.Data("Sqlite.update()", null),
                // task
                new MyListAdapter.Data("QueuedTasks.queue()", null),
                new MyListAdapter.Data("QueuedTasks.run()", null),
                new MyListAdapter.Data("QueuedTasks.running()", null),
                new MyListAdapter.Data("QueuedTasks.duration()", null),
                new MyListAdapter.Data("Tasks.queue()", null),
                new MyListAdapter.Data("Tasks.run()", null),
                new MyListAdapter.Data("Tasks.running()", null),
                new MyListAdapter.Data("Tasks.duration()", null)
        }, new MyListAdapter.Callback() {

            @Override
            public View myListAdapterCallback(final View view, final MyListAdapter.Data data) {
                ((TextView) view.findViewById(android.R.id.text1)).setText(data.key);
                if (data.value == null || data.value instanceof Runnable) {
                    ((TextView) view.findViewById(android.R.id.text2)).setText("-");
                    return view;
                }
                ((TextView) view.findViewById(android.R.id.text2)).setText(data.value.toString());
                return view;
            }

        });
        myListAdapter.index(getListView());
        setListAdapter(myListAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                final String key = myListAdapter.get(position).key;
                final Object value = myListAdapter.get(position).value;
                if (value != null) {
                    if (value instanceof Runnable) {
                        ((Runnable) value).run();
                    } else {
                        ActivityHelper.alertDialogBuilder(MainActivity.this)
                                .setTitle(key)
                                .setMessage(value.toString())
                                .setNeutralButton(android.R.string.ok, null)
                                .setCancelable(true)
                                .create()
                                .show();
                    }
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getSupportMenuInflater().inflate(me.shkschneider.skeleton.R.menu.skeleton, menu);

        final SearchManager searchManager = (SearchManager) SystemHelper.systemService(MainActivity.this, SystemHelper.SYSTEM_SERVICE_SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(me.shkschneider.skeleton.R.id.menu_search).getActionView();
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
                break ;

            case android.R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break ;

            case me.shkschneider.skeleton.R.id.author:
                ActivityHelper.alertDialogBuilder(MainActivity.this, me.shkschneider.skeleton.R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle(AUTHOR_NAME)
                        .setMessage(AUTHOR_URL)
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;

            case me.shkschneider.skeleton.R.id.license:
                ActivityHelper.alertDialogBuilder(MainActivity.this, me.shkschneider.skeleton.R.style.Theme_Skeleton_Dialog_Light)
                        .setTitle("Apache 2.0")
                        .setView(WebViewHelper.fromHtml(MainActivity.this,
                                FileHelper.readString(FileHelper.openRaw(MainActivity.this, me.shkschneider.skeleton.R.raw.license)).replaceAll("\n", "<br />")
                        ))
                        .setNegativeButton(android.R.string.ok, null)
                        .setCancelable(true)
                        .create()
                        .show();
                break ;

            case me.shkschneider.skeleton.R.id.code:
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
