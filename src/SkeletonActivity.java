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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.Window;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.FileHelper;
import me.shkschneider.skeleton.helper.NetworkHelper;
import me.shkschneider.skeleton.helper.WebViewHelper;
import me.shkschneider.skeleton.net.ImageDownloader;
import me.shkschneider.skeleton.net.WebService;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ((TextView) findViewById(R.id.platform)).setText("Platform: " + AndroidHelper.PLATFORM + " " + AndroidHelper.getRelease());
        ((TextView) findViewById(R.id.device)).setText("Device: " + AndroidHelper.getDevice());
        ((TextView) findViewById(R.id.id)).setText("ID: " + AndroidHelper.getDeviceId(getApplicationContext()));
        ((TextView) findViewById(R.id.ip)).setText("IP: " + NetworkHelper.ipAddresses().get(0));

        if (NetworkHelper.isConnectedToInternet(getApplicationContext())) {
            setSupportProgressBarIndeterminateVisibility(true);

            new ImageDownloader(getApplicationContext(), (ImageView) findViewById(R.id.image), "https://shkschneider.me/img/shkschneider.png").run(new ImageDownloader.Callback() {

                @Override
                public void ImageDownloaderCallback(final ImageView imageView, final Bitmap bitmap) {
                    ((Switch) findViewById(R.id.supportSwitch)).setChecked(bitmap != null);
                }

            });

            new WebService(getApplicationContext(), 0, "http://ifconfig.me/allure").run(new WebService.Callback() {

                @Override
                public void WebServiceCallback(final Integer id, final WebService.Response response) {
                    setSupportProgressBarIndeterminateVisibility(false);

                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage(response.content)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(final DialogInterface dialogInterface, final int i) {
                                    dialogInterface.dismiss();
                                }

                            })
                            .create()
                            .show();
                }

            });
        }
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(final int featureId, final MenuItem item) {
		switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_about:
                new AlertDialog.Builder(MainActivity.this)
                        .setView(WebViewHelper.loadHtml(getApplicationContext(), FileHelper.read(FileHelper.openRaw(getApplicationContext(), R.raw.about))))
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface dialogInterface, final int i) {
                                dialogInterface.dismiss();
                            }

                        })
                        .create()
                        .show();
                return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
