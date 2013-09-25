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
package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.Context;
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

    public static final String BROADCAST_TIME_TICK = android.content.Intent.ACTION_TIME_TICK;
    public static final String BROADCAST_TIME_CHANGED = android.content.Intent.ACTION_TIME_CHANGED;
    public static final String BROADCAST_TIMEZONE_CHANGED = android.content.Intent.ACTION_TIMEZONE_CHANGED;
    public static final String BROADCAST_BOOT_COMPLETED = android.content.Intent.ACTION_BOOT_COMPLETED;
    public static final String BROADCAST_PACKAGE_ADDED = android.content.Intent.ACTION_PACKAGE_ADDED;
    public static final String BROADCAST_PACKAGE_CHANGED = android.content.Intent.ACTION_PACKAGE_CHANGED;
    public static final String BROADCAST_PACKAGE_REMOVED = android.content.Intent.ACTION_PACKAGE_REMOVED;
    public static final String BROADCAST_PACKAGE_RESTARTED = android.content.Intent.ACTION_PACKAGE_RESTARTED;
    public static final String BROADCAST_PACKAGE_DATA_CLEARED = android.content.Intent.ACTION_PACKAGE_DATA_CLEARED;
    public static final String BROADCAST_UID_REMOVED = android.content.Intent.ACTION_UID_REMOVED;
    public static final String BROADCAST_BATTERY_CHANGED = android.content.Intent.ACTION_BATTERY_CHANGED;
    public static final String BROADCAST_POWER_CONNECTED = android.content.Intent.ACTION_POWER_CONNECTED;
    public static final String BROADCAST_POWER_DISCONNECTED = android.content.Intent.ACTION_POWER_DISCONNECTED;
    public static final String BROADCAST_SHUTDOWN = android.content.Intent.ACTION_SHUTDOWN;

    public static Boolean canHandle(final Context context, final android.content.Intent intent) {
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                return (resolveInfos.size() > 0);
            }
            else {
                LogHelper.w("PackageManager was NULL");
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return false;
    }

    public static void web(final Activity activity, final String url) {
        if (! TextUtils.isEmpty(url)) {
            if (NetworkHelper.validUrl(url)) {
                if (activity != null) {
                    activity.startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
                }
                else {
                    LogHelper.w("Activity was NULL");
                }
            }
            else {
                LogHelper.w("Url was invalid");
            }
        }
        else {
            LogHelper.w("Url was NULL");
        }
    }

    public static void market(final Activity activity, final String pkg) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pkg));
        if (activity != null) {
            activity.startActivity(intent);
        }
        else {
            LogHelper.w("Activity was NULL");
        }
    }

    public static void market(final Activity activity) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + AndroidHelper.packageName(activity)));
        if (activity != null) {
            activity.startActivity(intent);
        }
        else {
            LogHelper.w("Activity was NULL");
        }
    }

    public static void email(final Activity activity, final String[] to, final String subject, final String text) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, to);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        if (activity != null) {
            activity.startActivity(android.content.Intent.createChooser(intent, "Send An Email"));
        }
        else {
            LogHelper.w("Activity was NULL");
        }
    }

    public static void image(final Activity activity, final Uri uri) {
        final android.content.Intent intent = new android.content.Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (uri != null) {
            intent.setDataAndType(uri, "image/*");
            if (activity != null) {
                activity.startActivity(intent);
            }
            else {
                LogHelper.w("Activity was NULL");
            }
        }
        else {
            LogHelper.w("Uri was NULL");
        }
    }

    protected static final int REQUEST_CODE_CAMERA = 111;

    public static void camera(final Activity activity) {
        if (activity != null) {
            if (FeaturesHelper.feature(activity, FeaturesHelper.CAMERA)) {
                final android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (canHandle(activity, intent)) {
                    activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                else {
                    LogHelper.w("Cannot handle Intent");
                }
            }
            else {
                LogHelper.w("CAMERA was unavailable");
            }
        }
        else {
            LogHelper.w("Activity was NULL");
        }
    }

    protected static final int REQUEST_CODE_GALLERY = 222;

    public static void gallery(final Activity activity) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_PICK);
        intent.setType("image/*");
        if (activity != null) {
            activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
        }
        else {
            LogHelper.w("Activity was NULL");
        }
    }

    public static Bitmap onActivityResult(final Context context, final int requestCode, final int resultCode, final android.content.Intent intent) {
        if (context != null) {
            if (intent != null) {
                if (resultCode == Activity.RESULT_OK) {
                    switch (requestCode) {
                        case REQUEST_CODE_CAMERA:
                            final Bundle bundle = intent.getExtras();
                            if (bundle != null) {
                                return (Bitmap) bundle.get("data");
                            }
                            else {
                                LogHelper.w("Bundle was NULL");
                            }
                            break ;
                        case REQUEST_CODE_GALLERY:
                            final Uri uri = intent.getData();
                            if (uri != null) {
                                try {
                                    final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                                    if (inputStream != null) {
                                        return BitmapHelper.decodeUri(context, uri);
                                    }
                                    else {
                                        LogHelper.w("InputStream was NULL");
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    LogHelper.e("FileNotFoundException: " + e.getMessage());
                                }
                            }
                            else {
                                LogHelper.w("Uri was NULL");
                            }
                            break ;
                    }
                }
                else {
                    LogHelper.d("ResultCode was KO");
                }
            }
            else {
                LogHelper.w("Intent was NULL");
            }
        }
        else {
            LogHelper.w("Context was NULL");
        }
        return null;
    }

}
