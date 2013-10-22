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
package me.shkschneider.skeleton.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import me.shkschneider.skeleton.net.NetworkHelper;

@SuppressWarnings("unused")
public class IntentHelper {

    protected static final int REQUEST_CODE_CAMERA = 111;
    protected static final int REQUEST_CODE_GALLERY = 222;

    public static final String BROADCAST_TIME_TICK = Intent.ACTION_TIME_TICK;
    public static final String BROADCAST_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
    public static final String BROADCAST_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;
    public static final String BROADCAST_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;
    public static final String BROADCAST_PACKAGE_ADDED = Intent.ACTION_PACKAGE_ADDED;
    public static final String BROADCAST_PACKAGE_CHANGED = Intent.ACTION_PACKAGE_CHANGED;
    public static final String BROADCAST_PACKAGE_REMOVED = Intent.ACTION_PACKAGE_REMOVED;
    public static final String BROADCAST_PACKAGE_RESTARTED = Intent.ACTION_PACKAGE_RESTARTED;
    public static final String BROADCAST_PACKAGE_DATA_CLEARED = Intent.ACTION_PACKAGE_DATA_CLEARED;
    public static final String BROADCAST_UID_REMOVED = Intent.ACTION_UID_REMOVED;
    public static final String BROADCAST_BATTERY_CHANGED = Intent.ACTION_BATTERY_CHANGED;
    public static final String BROADCAST_POWER_CONNECTED = Intent.ACTION_POWER_CONNECTED;
    public static final String BROADCAST_POWER_DISCONNECTED = Intent.ACTION_POWER_DISCONNECTED;
    public static final String BROADCAST_SHUTDOWN = Intent.ACTION_SHUTDOWN;

    public static Boolean canHandle(final Context context, final Intent intent) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        final PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            LogHelper.w("PackageManager was NULL");
            return false;
        }

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    public static Boolean web(final Activity activity, final String url) {
        if (TextUtils.isEmpty(url)) {
            LogHelper.w("Url was NULL");
            return false;
        }

        if (! NetworkHelper.validUrl(url)) {
            LogHelper.w("Url was invalid");
            return false;
        }

        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        return true;
    }

    public static Boolean market(final Activity activity, final String pkg) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pkg));
        activity.startActivity(intent);
        return true;
    }

    public static Boolean market(final Activity activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + AndroidHelper.packageName(activity)));
        activity.startActivity(intent);
        return true;
    }

    public static Boolean email(final Activity activity, final String[] to, final String subject, final String text) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(intent, "Send An Email"));
        return true;
    }

    public static Boolean image(final Activity activity, final Uri uri) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        if (uri == null) {
            LogHelper.w("Uri was NULL");
            return false;
        }

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        activity.startActivity(intent);
        return true;
    }

    public static Boolean camera(final Activity activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        if (! FeaturesHelper.feature(activity, FeaturesHelper.CAMERA)) {
            LogHelper.w("CAMERA was unavailable");
            return false;
        }

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (! canHandle(activity, intent)) {
            LogHelper.w("Cannot handle Intent");
            return false;
        }

        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return true;
    }

    public static Boolean gallery(final Activity activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
        return true;
    }

    public static Bitmap onActivityResult(final Context context, final int requestCode, final int resultCode, final Intent intent) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        if (intent == null) {
            LogHelper.w("Intent was NULL");
            return null;
        }

        if (resultCode != Activity.RESULT_OK) {
            LogHelper.d("ResultCode was KO");
            return null;
        }

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                final Bundle bundle = intent.getExtras();
                if (bundle == null) {
                    LogHelper.w("Bundle was NULL");
                    return null;
                }

                return (Bitmap) bundle.get("data");

            case REQUEST_CODE_GALLERY:
                final Uri uri = intent.getData();
                if (uri == null) {
                    LogHelper.w("Uri was NULL");
                    return null;
                }

                try {
                    final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    if (inputStream == null) {
                        LogHelper.w("InputStream was NULL");
                        return null;
                    }

                    return BitmapHelper.decodeUri(context, uri);
                }
                catch (FileNotFoundException e) {
                    LogHelper.e("FileNotFoundException: " + e.getMessage());
                    return null;
                }

            default:
                break ;
        }
        return null;
    }

}
