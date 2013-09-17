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
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class SkeletonActivity extends SherlockActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skeleton);

        String message = "";
        message = message.concat("Android.getApi: " + Skeleton.Android.getAccount(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getSignature: " + Skeleton.Android.getSignature(SkeletonActivity.this) + "\n");
        message = message.concat("Android.isTablet: " + Skeleton.Android.isTablet(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getId: " + Skeleton.Android.getId(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getDeviceId: " + Skeleton.Android.getDeviceId(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getUUID: " + Skeleton.Android.getUUID(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getRandomId: " + Skeleton.Android.getRandomId() + "\n");
        message = message.concat("Android.getDevice: " + Skeleton.Android.getDevice() + "\n");
        message = message.concat("Android.getRelease: " + Skeleton.Android.getRelease() + "\n");
        message = message.concat("Android.getApi: " + Skeleton.Android.getApi() + "\n");
        message = message.concat("Android.isDebug: " + Skeleton.Android.isDebug() + "\n");
        message = message.concat("Android.getPackage: " + Skeleton.Android.getPackage(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getName: " + Skeleton.Android.getName(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getVersionName: " + Skeleton.Android.getVersionName(SkeletonActivity.this) + "\n");
        message = message.concat("Android.getVersionCode: " + Skeleton.Android.getVersionCode(SkeletonActivity.this) + "\n");

        final TextView textView = (TextView) findViewById(R.id.textView);
        if (textView != null) {
            textView.setText(message);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SkeletonActivity.this, SkeletonActivity.class));
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
