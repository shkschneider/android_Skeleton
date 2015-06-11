package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.data.FileHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.SkeletonApplication;

public class ApplicationHelper {

    public static Context context() {
        return SkeletonApplication.CONTEXT;
    }

    public static boolean debug() {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUG;
    }

    public static Resources resources() {
        return ApplicationHelper.context().getResources();
    }

    public static AssetManager assetManager() {
        return ApplicationHelper.context().getAssets();
    }

    public static String[] files() {
        return ApplicationHelper.context().fileList();
    }

    public static int wipe() {
        int i = 0;
        for (final String path : files()) {
            final File file = FileHelper.get(path);
            if (file.exists()) {
                if (file.delete()) {
                    i++;
                }
            }
        }
        return i;
    }

    public static String packageName() {
        return ApplicationHelper.context().getPackageName();
    }

    public static PackageManager packageManager() {
        return ApplicationHelper.context().getPackageManager();
    }

    public static String name() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                Log.w("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                Log.w("ApplicationInfo was NULL");
                return null;
            }

            final CharSequence label = applicationInfo.loadLabel(packageManager);
            if (label == null) {
                Log.w("Label was NULL");
                return null;
            }

            return label.toString();
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

    public static String versionName() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                Log.w("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionName;
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

    public static int versionCode() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                Log.w("PackageManager was NULL");
                return -1;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionCode;
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return -1;
        }
    }

    public static Bitmap icon() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                Log.w("PackageManager was NULL");
                return null;
            }

            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName(), 0);
            if (applicationInfo == null) {
                Log.w("ApplicationInfo was NULL");
                return null;
            }

            final Drawable drawable = applicationInfo.loadIcon(packageManager);
            return BitmapHelper.fromDrawable(drawable);
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

    public static List<String> permissions() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                Log.w("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_PERMISSIONS);

            final List<String> list = new ArrayList<String>();
            Collections.addAll(list, packageInfo.requestedPermissions);
            return list;
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

    public static String signature() {
        final PackageManager packageManager = packageManager();
        if (packageManager == null) {
            Log.w("PackageManager was NULL");
            return null;
        }

        try {
            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null) {
                Log.w("PackageInfo was NULL");
                return null;
            }

            final Signature[] signatures = packageInfo.signatures;
            if (signatures == null) {
                Log.d("No signatures");
                return null;
            }

            return signatures[0].toCharsString();
        }
        catch (final Exception e) {
            Log.wtf(null, e);
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
        return (Settings.Secure.getInt(ApplicationHelper.context().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_17)
    @SuppressWarnings("deprecation")
    public static boolean fromMarket17() {
        // API-17+ Settings.Global
        return (Settings.Global.getInt(ApplicationHelper.context().getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

    @TargetApi(AndroidHelper.API_3)
    public static boolean fromMarket3() {
        // API-3+ Settings.Secure
        return (Settings.Secure.getInt(ApplicationHelper.context().getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 0) == 1);
    }

}
