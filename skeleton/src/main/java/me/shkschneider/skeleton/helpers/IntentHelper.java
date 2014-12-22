package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.NetworkHelper;

import java.util.List;

public class IntentHelper {

    public static final int HOME_FLAGS = Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

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

    public static boolean canHandle(@NonNull final Intent intent) {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    public static boolean home(@NonNull final Activity activity, @NonNull final Class cls) {
        Intent intent = new Intent(activity, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
        return true;
    }

    public static boolean web(@NonNull final Activity activity, @NonNull final String url) {
        if (! NetworkHelper.validUrl(url)) {
            LogHelper.warning("Url was invalid");
            return false;
        }

        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        return true;
    }

    public static boolean market(@NonNull final Activity activity, @NonNull final String pkg) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?androidId=" + pkg));
        activity.startActivity(intent);
        return true;
    }

    public static boolean market(@NonNull final Activity activity) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?androidId=" + ApplicationHelper.packageName()));
        activity.startActivity(intent);
        return true;
    }

    public static boolean email(@NonNull final Activity activity, @NonNull final String[] to, final String subject, final String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        if (! StringHelper.nullOrEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (! StringHelper.nullOrEmpty(text)) {
            intent.putExtra(Intent.EXTRA_TEXT, text);
        }
        activity.startActivity(Intent.createChooser(intent, "Send An Email"));
        return true;
    }

    public static boolean image(@NonNull final Activity activity, @NonNull final Uri uri) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        activity.startActivity(intent);
        return true;
    }

    public static boolean camera(@NonNull final Activity activity) {
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

    public static boolean gallery(@NonNull final Activity activity) {
        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
        return true;
    }

    public static boolean settings(@NonNull final Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
        return true;
    }

    public static Bitmap onActivityResult(final int requestCode, final int resultCode, @NonNull final Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            LogHelper.debug("ResultCode was not OK");
            return null;
        }

        if (requestCode == REQUEST_CODE_CAMERA) {
            final Bundle bundle = intent.getExtras();
            if (bundle == null) {
                LogHelper.warning("Bundle was NULL");
                return null;
            }

            return (Bitmap) bundle.get("data");
        }
        else if (requestCode == REQUEST_CODE_GALLERY) {
            final Uri uri = intent.getData();
            if (uri == null) {
                LogHelper.warning("Uri was NULL");
                return null;
            }

            return BitmapHelper.decodeUri(uri);
        }
        return null;
    }

}
