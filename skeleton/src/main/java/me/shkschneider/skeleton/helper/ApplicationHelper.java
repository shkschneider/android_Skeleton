package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.BuildConfig;
import me.shkschneider.skeleton.ImageManipulator;
import me.shkschneider.skeleton.SkeletonApplication;

public class ApplicationHelper {

    public static boolean debug() {
        // Workaround to get DEBUG value of the android application and not that android-library
        try {
            final Class<?> cls = Class.forName(packageName() + ".BuildConfig");
            final Field field = cls.getField("DEBUG");
            return (Boolean) field.get(false);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
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

    public static PackageManager packageManager() {
        return SkeletonApplication.CONTEXT.getPackageManager();
    }

    public static String name() {
        try {
            final PackageManager packageManager = packageManager();
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
            final PackageManager packageManager = packageManager();
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
            final PackageManager packageManager = packageManager();
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

    public static Bitmap icon() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                LogHelper.warning("ApplicationInfo was NULL");
                return null;
            }

            final Drawable drawable = applicationInfo.loadIcon(packageManager);
            return ImageManipulator.fromDrawable(drawable);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static List<String> permissions() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_PERMISSIONS);

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
        final PackageManager packageManager = packageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_SIGNATURES);
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

    public static boolean fromMarket() {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            return fromMarket21();
        }
        else if (AndroidHelper.api() >= AndroidHelper.API_17) {
            return fromMarket17();
        }
        return fromMarket3();
    }

    @TargetApi(AndroidHelper.API_21)
    public static boolean fromMarket21() {
        // API-21+ Settings.Secure
        return (Settings.Secure.getInt(SkeletonApplication.CONTEXT.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_17)
    @SuppressWarnings("deprecation")
    public static boolean fromMarket17() {
        // API-17+ Settings.Global
        return (Settings.Global.getInt(SkeletonApplication.CONTEXT.getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_3)
    public static boolean fromMarket3() {
        // API-3+ Settings.Secure
        return (Settings.Secure.getInt(SkeletonApplication.CONTEXT.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

}
