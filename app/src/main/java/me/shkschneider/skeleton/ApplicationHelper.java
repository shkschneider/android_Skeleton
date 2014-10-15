package me.shkschneider.skeleton;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.app.BuildConfig;
import me.shkschneider.app.MainApplication;

public class ApplicationHelper {

    public static boolean debug() {
        return BuildConfig.DEBUG;
    }

    public static Resources resources() {
        return MainApplication.CONTEXT.getResources();
    }

    public static AssetManager assets() {
        return MainApplication.CONTEXT.getAssets();
    }

    public static String packageName() {
        return MainApplication.CONTEXT.getPackageName();
    }

    public static String name() {
        try {
            final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                LogHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            final CharSequence label = applicationInfo.loadLabel(packageManager);
            if (label == null) {
                LogHelper.warning("Label was NULL");
                return null;
            }

            return label.toString();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String versionName() {
        try {
            final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionName;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static int versionCode() {
        try {
            final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return -1;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionCode;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return -1;
        }
    }

    public static Drawable icon() {
        try {
            final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                LogHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            return applicationInfo.loadIcon(packageManager);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static List<String> permissions() {
        try {
            final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(ApplicationHelper.packageName(), PackageManager.GET_PERMISSIONS);

            final List<String> list = new ArrayList<String>();
            Collections.addAll(list, packageInfo.requestedPermissions);
            return list;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String signature() {
        final PackageManager packageManager = MainApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(MainApplication.CONTEXT.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null) {
                LogHelper.warning("PackageInfo was NULL");
                return null;
            }

            final Signature[] signatures = packageInfo.signatures;
            if (signatures == null) {
                LogHelper.debug("No signatures");
                return null;
            }

            return signatures[0].toCharsString();
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static boolean settings(final Activity activity) {
        final int api = AndroidHelper.api();
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", packageName(), null));
        activity.startActivity(intent);
        return true;
    }

}
