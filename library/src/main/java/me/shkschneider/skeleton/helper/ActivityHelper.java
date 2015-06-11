package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class ActivityHelper {

    public static View contentView(@NonNull final Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Deprecated
    public static void toast(@NonNull final String msg) {
        Toast.makeText(ApplicationHelper.context(), msg, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Deprecated
    public static void snackBar(@NonNull final View view, @NonNull final String msg, @NonNull final String action, @NonNull final View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, onClickListener)
                .show();
    }

    public static boolean portrait() {
        return (ApplicationHelper.context().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public static boolean landscape() {
        return (ApplicationHelper.context().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public static String title(@NonNull final Activity activity) {
        final ActivityInfo activityInfo = activityInfo(activity);
        if (activityInfo == null) {
            Log.w("ActivityInfo was NULL");
            return null;
        }

        return activity.getResources().getString(activityInfo.labelRes);
    }

    // TODO subtitle()

    public static ActivityInfo activityInfo(@NonNull final Activity activity) {
        final PackageManager packageManager = activity.getPackageManager();
        final ComponentName componentName = activity.getComponentName();
        try {
            return packageManager.getActivityInfo(componentName, 0);
        }
        catch (final Exception e) {
            Log.wtf(null, e);
            return null;
        }
    }

}
