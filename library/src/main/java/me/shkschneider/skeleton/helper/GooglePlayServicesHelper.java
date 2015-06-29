package me.shkschneider.skeleton.helper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;
import java.util.List;

public class GooglePlayServicesHelper {

    protected GooglePlayServicesHelper() {
        // Empty
    }

    public static final String GOOGLE_ACCOUNT_TYPE = GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE;

    public static int status() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(ApplicationHelper.context());
    }

    public static boolean check() {
        return (status() == ConnectionResult.SUCCESS);
    }

    public static Dialog dialog(@NonNull final Activity activity) {
        if (check()) {
            LogHelper.d("GooglePlayServices was OK");
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

    public static List<String> accounts() {
        final List<String> accounts = new ArrayList<String>();
        final AccountManager accountManager = AccountManager.get(ApplicationHelper.context());
        if (accountManager != null) {
            for (Account account : accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE)) {
                if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                    accounts.add(account.name);
                }
            }
        }
        else {
            LogHelper.w("AccountManager was NULL");
        }
        return accounts;
    }

    public static String account() {
        final List<String> accounts = accounts();
        if (accounts.size() == 0) {
            return null;
        }

        return accounts.get(0);
    }

}
