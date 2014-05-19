package me.shkschneider.skeleton.helpers;

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

import me.shkschneider.skeleton.BuildConfig;
import me.shkschneider.skeleton.SkeletonApplication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationHelper {

    public static boolean debug() {
        return BuildConfig.DEBUG;
    }

    public static Resources resources() {
        return SkeletonApplication.CONTEXT.getResources();
    }

    public static AssetManager assets() {
        return SkeletonApplication.CONTEXT.getAssets();
    }

    public static String packageName() {
        return SkeletonApplication.CONTEXT.getPackageName();
    }

    public static String name() {
        try {
            final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
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
            final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
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

    public static Integer versionCode() {
        try {
            final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionCode;
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Drawable icon() {
        try {
            final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
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
            final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
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

    public static String installer(@NotNull final String packageName) {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        return packageManager.getInstallerPackageName(packageName);
    }

    public static String installer() {
        return installer(packageName());
    }

    public static String signature() {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(SkeletonApplication.CONTEXT.getPackageName(), PackageManager.GET_SIGNATURES);
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

    public static boolean settings(@NotNull final Activity activity) {
        final int api = AndroidHelper.api();
        final Intent intent = new Intent();
        if (api < AndroidHelper.API_9) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((api == AndroidHelper.API_8 ? "pkg" : "com.android.settings.ApplicationPkgName"), packageName());
        }
        else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName(), null));
        }
        activity.startActivity(intent);
        return true;
    }

}
