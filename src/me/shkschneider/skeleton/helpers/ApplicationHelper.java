package me.shkschneider.skeleton.helpers;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import com.maximiles.nfc.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.SkeletonApplication;

@SuppressWarnings("unused")
public final class ApplicationHelper {

    public static boolean debug() {
        return BuildConfig.DEBUG;
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
            return null;
        }
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.error("NameNotFoundException: " + e.getMessage());
            return null;
        }
    }

}
