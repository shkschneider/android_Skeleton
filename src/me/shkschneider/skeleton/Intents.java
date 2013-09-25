package me.shkschneider.skeleton;

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

@SuppressWarnings("unused")
public class Intents {

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
                Log.w("PackageManager was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return false;
    }

    public static void web(final Activity activity, final String url) {
        if (! TextUtils.isEmpty(url)) {
            if (Networks.validUrl(url)) {
                if (activity != null) {
                    activity.startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
                }
                else {
                    Log.w("Activity was NULL");
                }
            }
            else {
                Log.w("Url was invalid");
            }
        }
        else {
            Log.w("Url was NULL");
        }
    }

    public static void market(final Activity activity, final String pkg) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pkg));
        if (activity != null) {
            activity.startActivity(intent);
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void market(final Activity activity) {
        final android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + Skeleton.Android.packageName(activity)));
        if (activity != null) {
            activity.startActivity(intent);
        }
        else {
            Log.w("Activity was NULL");
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
            Log.w("Activity was NULL");
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
                Log.w("Activity was NULL");
            }
        }
        else {
            Log.w("Uri was NULL");
        }
    }

    protected static final int REQUEST_CODE_CAMERA = 111;

    public static void camera(final Activity activity) {
        if (activity != null) {
            if (Features.feature(activity, Features.CAMERA)) {
                final android.content.Intent intent = new android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (canHandle(activity, intent)) {
                    activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                else {
                    Log.w("Cannot handle Intent");
                }
            }
            else {
                Log.w("CAMERA was unavailable");
            }
        }
        else {
            Log.w("Activity was NULL");
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
            Log.w("Activity was NULL");
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
                                Log.w("Bundle was NULL");
                            }
                            break ;
                        case REQUEST_CODE_GALLERY:
                            final Uri uri = intent.getData();
                            if (uri != null) {
                                try {
                                    final InputStream inputStream = context.getContentResolver().openInputStream(uri);
                                    if (inputStream != null) {
                                        return Graphics.decodeUri(context, uri);
                                    }
                                    else {
                                        Log.w("InputStream was NULL");
                                    }
                                }
                                catch (FileNotFoundException e) {
                                    Log.e("FileNotFoundException: " + e.getMessage());
                                }
                            }
                            else {
                                Log.w("Uri was NULL");
                            }
                            break ;
                    }
                }
                else {
                    Log.d("ResultCode was KO");
                }
            }
            else {
                Log.w("Intent was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

}
