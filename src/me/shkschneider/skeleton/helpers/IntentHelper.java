package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.NetworkHelper;

@SuppressWarnings("unused")
public final class IntentHelper {

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

    private static final int REQUEST_CODE_CAMERA = 111;
    private static final int REQUEST_CODE_GALLERY = 222;

    public static boolean canHandle(final Intent intent) {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    public static boolean web(final Activity activity, final String url) {
        if (StringHelper.nullOrEmpty(url) || ! NetworkHelper.validUrl(url)) {
            LogHelper.warning("Url was NULL");
            return false;
        }
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static boolean market(final Activity activity, final String pkg) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + pkg));
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static boolean market(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + ApplicationHelper.packageName()));
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static boolean email(final Activity activity, final String[] to, final String subject, final String text) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(Intent.createChooser(intent, "Send An Email"));
        return true;
    }

    public static boolean image(final Activity activity, final Uri uri) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (uri == null) {
            LogHelper.warning("Uri was NULL");
            return false;
        }

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static boolean camera(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (! FeaturesHelper.feature(FeaturesHelper.CAMERA)) {
            LogHelper.warning("Camera was unavailable");
            return false;
        }

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return true;
    }

    public static boolean gallery(final Activity activity) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }

        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
        return true;
    }

    public static boolean directions(final Activity activity, final LatLng from, final LatLng to) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (from == null) {
            LogHelper.warning("From was NULL");
            return false;
        }
        if (to == null) {
            LogHelper.warning("To was NULL");
            return false;
        }

        final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                        from.latitude,
                        from.longitude,
                        to.latitude,
                        to.longitude)));
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static boolean facebook(final Activity activity, final String content) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (content == null) {
            LogHelper.warning("Content was NULL");
            return false;
        }

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager packageManager = activity.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        for (final ResolveInfo resolveInfo : resolveInfos) {
            final ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo == null) {
                continue ;
            }
            if (activityInfo.name == null) {
                continue ;
            }
            if (activityInfo.name.contains("facebook")) {
                final ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                if (applicationInfo == null) {
                    continue ;
                }
                final ComponentName componentName = new ComponentName(applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(componentName);
                if (! canHandle(intent)) {
                    LogHelper.warning("Cannot handle Intent");
                    return false;
                }

                activity.startActivity(intent);
                return true;
            }
        }

        return false;
    }

    public static boolean twitter(final Activity activity, final String content) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (content == null) {
            LogHelper.warning("Content was NULL");
            return false;
        }

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager packageManager = activity.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        for (final ResolveInfo resolveInfo : resolveInfos) {
            final ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo == null) {
                continue ;
            }
            if (activityInfo.name == null) {
                continue ;
            }
            if (activityInfo.name.contains("com.twitter.android.PostActivity")) {
                final ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                if (applicationInfo == null) {
                    continue ;
                }
                final ComponentName componentName = new ComponentName(applicationInfo.packageName, activityInfo.name);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(componentName);
                if (! canHandle(intent)) {
                    LogHelper.warning("Cannot handle Intent");
                    return false;
                }

                activity.startActivity(intent);
                return true;
            }
        }

        return false;
    }

    public static boolean settings(final Activity activity, final String packageName) {
        if (activity == null) {
            LogHelper.warning("Activity was NULL");
            return false;
        }
        if (StringHelper.nullOrEmpty(packageName)) {
            LogHelper.warning("PackageName was NULL");
            return false;
        }

        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(packageName));
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivity(intent);
        return true;
    }

    public static Bitmap onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return null;
        }
        if (resultCode != Activity.RESULT_OK) {
            LogHelper.debug("ResultCode was not OK");
            return null;
        }

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                final Bundle bundle = intent.getExtras();
                if (bundle == null) {
                    LogHelper.warning("Bundle was NULL");
                    return null;
                }

                return (Bitmap) bundle.get("data");

            case REQUEST_CODE_GALLERY:
                final Uri uri = intent.getData();
                if (uri == null) {
                    LogHelper.warning("Uri was NULL");
                    return null;
                }

                return BitmapHelper.decodeUri(uri);

            default:
                break ;
        }
        return null;
    }

}
