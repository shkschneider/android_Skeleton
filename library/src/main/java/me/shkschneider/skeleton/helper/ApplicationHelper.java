package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shkschneider.skeleton.java.SkHide;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.SkeletonApplication;

public class ApplicationHelper {

    public static final int DEFAULT_ICON = android.R.drawable.sym_def_app_icon;
    public static final String INSTALLER = "com.android.vending";

    protected ApplicationHelper() {
        // Empty
    }

    public static boolean debug() {
        // <https://stackoverflow.com/a/25517680/603270>
        return SkeletonApplication.DEBUG;
    }

    public static Resources resources() {
        return ContextHelper.applicationContext().getResources();
    }

    @Deprecated //Avoid
    public static AssetManager assetManager() {
        return AssetsHelper.assetManager();
    }

    @Deprecated // Discouraged
    public static String[] files() {
        return ContextHelper.applicationContext().fileList();
    }

    public static String packageName() {
        return ContextHelper.applicationContext().getPackageName();
    }

    public static PackageManager packageManager() {
        return ContextHelper.applicationContext().getPackageManager();
    }

    @Nullable
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    // <http://semver.org>
    @Size(3)
    @Nullable
    public static Integer[] semanticVersions() {
        final String versionName = versionName();
        if (versionName == null) {
            return null;
        }
        if (! versionName.matches("^\\d+\\.\\d+\\.\\d+$")) {
            LogHelper.warning("Not semantic versioning");
            return null;
        }
        final String[] versionNames = versionName.split("\\.");
        return new Integer[] {
                Integer.valueOf(versionNames[0]),
                Integer.valueOf(versionNames[1]),
                Integer.valueOf(versionNames[2])
        };
    }

    @Nullable
    public static String versionName() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            return packageManager.getPackageInfo(packageName(), PackageManager.GET_META_DATA).versionName;
        }
        catch (final PackageManager.NameNotFoundException e) {
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return -1;
        }
    }

    @Nullable
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
            return BitmapHelper.fromDrawable(drawable);
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @Nullable
    public static List<String> permissions() {
        try {
            final PackageManager packageManager = packageManager();
            if (packageManager == null) {
                LogHelper.warning("PackageManager was NULL");
                return null;
            }

            final PackageInfo packageInfo = packageManager.getPackageInfo(packageName(), PackageManager.GET_PERMISSIONS);

            final List<String> list = new ArrayList<>();
            Collections.addAll(list, packageInfo.requestedPermissions);
            return list;
        }
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    @Nullable
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
        catch (final PackageManager.NameNotFoundException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String installer() {
        return installer(packageName());
    }

    @SkHide
    public static String installer(@NonNull final String packageName) {
        return packageManager().getInstallerPackageName(packageName);
    }

    public static boolean installedFromGooglePlay() {
        return installer().equalsIgnoreCase(INSTALLER);
    }

}
