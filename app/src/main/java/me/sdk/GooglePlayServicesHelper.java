package me.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import me.app.MainApplication;

public class GooglePlayServicesHelper {

    public static int status() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainApplication.CONTEXT);
    }

    public static boolean check() {
        return (status() == ConnectionResult.SUCCESS);
    }

    public static Dialog dialog(final Activity activity) {
        if (check()) {
            LogHelper.debug("GooglePlayServices was OK");
            return null;
        }

        final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status(), activity, 0);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(final DialogInterface dialogInterface) {
                dialog.dismiss();
            }

        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(final DialogInterface dialogInterface) {
                activity.finish();
            }

        });
        return dialog;
    }

}
