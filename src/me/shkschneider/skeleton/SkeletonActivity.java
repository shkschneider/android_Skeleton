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
import android.location.Location;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkeletonActivity extends SherlockListActivity {

    private Map<String, String> map(final String key, final String value) {
        final Map<String, String> data = new HashMap<String, String>();
        data.put("1", key);
        data.put("2", value);
        return data;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Skeleton.Activity.indeterminate(SkeletonActivity.this);
        setContentView(R.layout.skeleton);

        refresh();
    }

    private void refresh() {
        Skeleton.Activity.indeterminate(SkeletonActivity.this, true);

        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        data.add(map("DEBUG", SkeletonApplication.DEBUG.toString()));
        data.add(map("TAG", SkeletonApplication.TAG));
        data.add(map("LOCALE", SkeletonApplication.LOCALE));

        data.add(map("Time.timestamp()", Skeleton.Time.timestamp().toString()));
        data.add(map("Android.account()", Skeleton.Android.account(SkeletonActivity.this)));
        data.add(map("Android.signature()", Skeleton.Android.signature(SkeletonActivity.this).replaceFirst("^(.{40}).*$", "$1 [...]")));
        data.add(map("Android.tablet()", Skeleton.Android.tablet(SkeletonActivity.this).toString()));
        data.add(map("Android.id()", Skeleton.Android.id(SkeletonActivity.this)));
        data.add(map("Android.deviceId()", Skeleton.Android.deviceId(SkeletonActivity.this)));
        data.add(map("Android.uuid()", Skeleton.Android.uuid(SkeletonActivity.this)));
        data.add(map("Android.randomId()", Skeleton.Android.randomId()));
        data.add(map("Android.codename()", Skeleton.Android.codename()));
        data.add(map("Android.manufacturer()", Skeleton.Android.manufacturer()));
        data.add(map("Android.device()", Skeleton.Android.device()));
        data.add(map("Android.release()", Skeleton.Android.release()));
        data.add(map("Android.api()", Skeleton.Android.api().toString()));
        data.add(map("Android.debug()", Skeleton.Android.debug().toString()));
        data.add(map("Android.packageName()", Skeleton.Android.packageName(SkeletonActivity.this)));
        data.add(map("Android.name()", Skeleton.Android.name(SkeletonActivity.this)));
        data.add(map("Android.versionName()", Skeleton.Android.versionName(SkeletonActivity.this)));
        data.add(map("Android.versionCode()", Skeleton.Android.versionCode(SkeletonActivity.this).toString()));
        data.add(map("System.uname()", Skeleton.System.uname()));
        data.add(map("File.internalDir()", Skeleton.File.internalDir(SkeletonActivity.this)));
        data.add(map("File.externalDir()", Skeleton.File.externalDir(SkeletonActivity.this)));
        data.add(map("File.internalCacheDir()", Skeleton.File.internalCacheDir(SkeletonActivity.this)));
        data.add(map("File.externalCacheDir()", Skeleton.File.externalCacheDir(SkeletonActivity.this)));
        data.add(map("File.downloadCache()", Skeleton.File.downloadCache()));
        data.add(map("File.sdCardAvailable()", Skeleton.File.sdCardAvailable().toString()));
        data.add(map("File.sdCard()", Skeleton.File.sdCard()));
        data.add(map("Audio.volume()", Skeleton.Audio.volume(SkeletonActivity.this).toString()));
        data.add(map("Network.defaultUserAgent()", Skeleton.Network.defaultUserAgent()));
        data.add(map("Network.userAgent()", Skeleton.Network.userAgent(SkeletonActivity.this)));
        data.add(map("Network.online()", Skeleton.Network.online(SkeletonActivity.this).toString()));
        data.add(map("Network.macAddress()", Skeleton.Network.macAddress(SkeletonActivity.this)));
        data.add(map("Network.ipAddresses()", Skeleton.Network.ipAddresses().toString()));
        data.add(map("Runtime.processors()", Skeleton.Runtime.processors().toString()));
        data.add(map("Runtime.freeMemory()", Skeleton.Runtime.freeMemory().toString()));
        data.add(map("Runtime.maxMemory()", Skeleton.Runtime.maxMemory().toString()));
        data.add(map("Runtime.totalMemory()", Skeleton.Runtime.totalMemory().toString()));
        data.add(map("Screen.isOn()", Skeleton.Screen.isOn(SkeletonActivity.this).toString()));
        data.add(map("Screen.density()", Skeleton.Screen.density(SkeletonActivity.this).toString()));
        data.add(map("Screen.height()", Skeleton.Screen.height(SkeletonActivity.this).toString()));
        data.add(map("Screen.width()", Skeleton.Screen.width(SkeletonActivity.this).toString()));
        data.add(map("Screen.orientation()", Skeleton.Screen.orientation(SkeletonActivity.this).toString()));

        final Skeleton.Location skeletonLocation = new Skeleton.Location(SkeletonActivity.this, null);
        skeletonLocation.start(false);
        final Location location = skeletonLocation.location();
        data.add(map("Location.location()", String.format("%s %f %f ~%d",
                location.getProvider(),
                location.getLatitude(),
                location.getLongitude(),
                Math.round(location.getAccuracy()))));
        skeletonLocation.stop();

        setListAdapter(new SimpleAdapter(SkeletonActivity.this,
                data,
                android.R.layout.simple_list_item_2,
                new String[] { "1", "2" },
                new int[] { android.R.id.text1, android.R.id.text2 }
        ));

        Skeleton.Activity.indeterminate(SkeletonActivity.this, true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getSupportMenuInflater().inflate(R.menu.skeleton, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SkeletonActivity.this, SkeletonActivity.class));
                break ;
            case R.id.skeleton:
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
